package com.eai.wizard.serviceimple;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eai.dto.Constants;
import com.eai.dto.Progress;
import com.eai.wizard.model.LevelFour;
import com.eai.wizard.model.LevelThree;
import com.eai.wizard.model.ViewLevelFour;
import com.eai.wizard.model.ViewLevelThree;
import com.eai.wizard.repository.LevelFourRepository;
import com.eai.wizard.repository.ViewLevelFourRepository;
import com.eai.wizard.service.LevelFourService;
import com.eai.wizard.service.LevelThreeService;

@Service
public class LevelFourServiceImpl implements LevelFourService {
	
	@PersistenceContext
    private EntityManager manager;
	
	@Autowired
	LevelThreeService levelThreeService;
	
	@Autowired
	ViewLevelFourRepository viewLevelFourRepository;
	
	@Autowired
	LevelFourRepository levelFourRepository;
		
	@Override
    @Transactional
    public void save(LevelFour levelFour){
		levelFourRepository.save(levelFour);
    }	
	
	@Override
    @Transactional
    public void save(List<ViewLevelThree> viewLevelThreeList, BigDecimal usdToCop, BigDecimal shippingUsd, BigDecimal shippingCop){
		String priceCop = "";
		LevelFour levelFour = null;
		for(ViewLevelThree viewLevelThree : viewLevelThreeList){
			Progress progress = Progress.getSingletonInstance();
			LevelThree levelThree = levelThreeService.findOne(viewLevelThree.getIdLevel3());
			ViewLevelFour viewLevelFour = viewLevelFourRepository.findById(viewLevelThree.getIdLevel3()).orElse(null);
			
			try {
				priceCop = this.priceValue(usdToCop, viewLevelFour.getPrice(), shippingUsd, shippingCop).toString();
				
				levelFour = 
						new LevelFour(
								viewLevelFour.getAlphabet(),
								viewLevelFour.getCategory(),
								viewLevelFour.getName(),
								viewLevelFour.getPrice(),
								priceCop,
								viewLevelFour.getAttachment(),
								viewLevelThree.getIdLevel3());
				
				this.save(levelFour);
				
				progress.setCountSuccess(progress.calculateCountSuccess());
				levelThree.setStatus(Constants.SUCCESS.val());
			}catch (Exception ex) {
    			progress.setCountError(progress.calculateCountError());
        		levelThree.setStatus(Constants.ERROR.val());
        	}
			
			levelThreeService.save(levelThree);
		}
    }

	@Override
	@Transactional
	public LevelFour findOne(Integer idLevel4) {
		return levelFourRepository.findById(idLevel4).orElse(null);
	}
	
	public BigDecimal priceValue(
            BigDecimal usdToCop,
            String priceUSD,
            BigDecimal shippingUsd,
            BigDecimal shippingCop){        
    	
    	BigDecimal priceCop = new BigDecimal(priceUSD).multiply(usdToCop);
        BigDecimal percentageGain = new BigDecimal(Constants.PERCENTAGE_GAIN.val());
        BigDecimal gain = priceCop.multiply(percentageGain).divide(new BigDecimal("100"));
        BigDecimal total = priceCop.add(gain).add(shippingUsd).add(shippingCop);
    
        return total.setScale(2, RoundingMode.DOWN);
    }
}