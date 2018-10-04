package com.eai.wizard.serviceimple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eai.dto.Constants;
import com.eai.dto.Progress;
import com.eai.wizard.model.LevelOne;
import com.eai.wizard.repository.LevelOneRepository;
import com.eai.wizard.service.LevelOneService;
import com.eai.wizard.utils.UtilPage;

@Service
public class LevelOneServiceImpl implements LevelOneService {
	
	private UtilPage utilPage;
	private Progress progress;
	
	@Autowired
	LevelOneRepository levelOneRepository;
	
	public LevelOneServiceImpl(){
		utilPage = new UtilPage();
        progress = Progress.getSingletonInstance();
    }
	
	@Override
    @Transactional
    public void save(LevelOne levelOne){
		levelOneRepository.save(levelOne);
    }

	@Override
	@Transactional
	public List<LevelOne> findPendingOrFail() {
		return levelOneRepository.findPendingOrFail();
	}

	@Override
	@Transactional
	public int findSuccess() {
		return levelOneRepository.findSuccess();
	}
	
	@Override
	@Transactional
	public void levelOne(LevelOne levelOne){
		try{
        	String url = utilPage.url(levelOne.getAlphabet());
            
            if(levelOne.getIdLevel1() == null) {
            	levelOne = new LevelOne(levelOne.getAlphabet(), url, Constants.PENDING.val());
            }
            
           	progress.setCountSuccess(progress.calculateCountSuccess());           
        }catch(Exception ex){
        	levelOne.setStatus(Constants.ERROR.val());
        	progress.setCountFail(progress.calculateCountFail());
        }
        
        this.save(levelOne);
    }
}