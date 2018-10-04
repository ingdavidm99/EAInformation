package com.eai.wizard.serviceimple;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eai.dto.Constants;
import com.eai.dto.Progress;
import com.eai.wizard.model.LevelThree;
import com.eai.wizard.model.LevelTwo;
import com.eai.wizard.model.ViewLevelThree;
import com.eai.wizard.model.ViewLevelTwo;
import com.eai.wizard.page.Page;
import com.eai.wizard.repository.LevelThreeRepository;
import com.eai.wizard.repository.ViewLevelThreeRepository;
import com.eai.wizard.service.LevelThreeService;
import com.eai.wizard.service.LevelTwoService;

@Service
public class LevelThreeServiceImpl implements LevelThreeService {
	
	private Page page;
    private Progress progress;
	
	@PersistenceContext
    private EntityManager manager;
	
	@Autowired
    private LevelThreeRepository levelThreeRepository;
	
	@Autowired
    private ViewLevelThreeRepository viewLevelThreeRepository;
	
	@Autowired
	LevelTwoService levelTwoService; 
	
	
	 public LevelThreeServiceImpl(){
	    page = new Page();
	   progress = Progress.getSingletonInstance();
	   }
    
	@Override
    @Transactional
    public void save(LevelThree levelThree){
		levelThreeRepository.save(levelThree);
    }
	
    @Override
    @Transactional
    public void saveAll(List<LevelThree> levelThreeList){
    	levelThreeRepository.saveAll(levelThreeList);
    }
    
    @SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ViewLevelThree> findPendingOrFail() {
    	
    	StringBuilder sql = new StringBuilder();
    	sql.append("SELECT * FROM view_level_three l WHERE l.status IN ('P','F')");
    	
    	Query query = manager.createNativeQuery(sql.toString(), ViewLevelThree.class);
    	
		return query.getResultList();
	}
	
	@Override
	@Transactional
	public int findSuccess() {
		return viewLevelThreeRepository.findSuccess();
	}
	
	@Override
	@Transactional
	public LevelThree findById(Integer idLevel3) {
		return levelThreeRepository.findById(idLevel3).orElse(null);
	}
	
	@Override
    @Transactional
	public void levelThree(
    		List<ViewLevelTwo> viewLevelTwoList, 
    		String userAgent,
    		int numberOfRetries){
    	
		List<LevelThree> levelThreeList = new ArrayList<>();	
		
    	for(ViewLevelTwo viewLevelTwo : viewLevelTwoList){
    		LevelTwo levelTwo = levelTwoService.findById(viewLevelTwo.getIdLevel2());
    		
    		try {
    			Element firstDiv = page.element(viewLevelTwo.getUrl(), "rightResultsATF", numberOfRetries, userAgent);
    			
	    		if(firstDiv != null){
	    			levelThreeList.clear();
	    			data(firstDiv, levelThreeList, viewLevelTwo.getIdLevel2());
	    		}
	    		
	    		if(!levelThreeList.isEmpty()){
	    			progress.setCountSuccess(progress.calculateCountSuccess());
	    			levelTwo.setStatus(Constants.SUCCESS.val());
	    			this.saveAll(levelThreeList);
	    		}else {
	    			progress.setCountFail(progress.calculateCountFail());
	    			levelTwo.setStatus(Constants.FAILURE.val());
	    		}
    		}catch (Exception ex) {
    			progress.setCountFail(progress.calculateCountFail());
    			levelTwo.setStatus(Constants.ERROR.val());
        	}
    	}
    }
    
    private void data(Element firstDiv, List<LevelThree> levelThreeList, Integer idLevel3) {
    	Elements articles = firstDiv.getElementsByClass("s-item-container");
		
    	for (Element article : articles) {
    		if(!article.getElementsByTag("a").isEmpty()){
	          	StringBuilder url = new StringBuilder();
	      		url.append(article.getElementsByTag("a").get(0).attr("href"));
	      		
	      		StringBuilder name = new StringBuilder();
	      		name.append(article.getElementsByTag("a").get(0).getElementsByTag("img").attr("alt"));
	      		
	      		StringBuilder price = new StringBuilder();
	      		price.append(article.getElementsByClass("sx-price-whole").text().replaceAll(",",""))
	      				.append(".")
	      				.append(article.getElementsByClass("sx-price-fractional").text());
	      		
	      		StringBuilder attachment = new StringBuilder();
	      		attachment.append(article.getElementsByTag("a").get(0).getElementsByTag("img").attr("src"));
      		
	      		if(!name.toString().isEmpty() && !url.toString().isEmpty()) {
	      			levelThreeList.add(
						new LevelThree(
							name.toString(), 
							price.toString(), 
							attachment.toString(), 
							url.toString(), 
							Constants.PENDING.val(), 
							idLevel3
						));
	      		}
          	}
		}  
    }
}