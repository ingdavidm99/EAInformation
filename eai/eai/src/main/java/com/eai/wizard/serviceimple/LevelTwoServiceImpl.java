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
import com.eai.wizard.model.LevelOne;
import com.eai.wizard.model.LevelTwo;
import com.eai.wizard.model.ViewLevelTwo;
import com.eai.wizard.page.Page;
import com.eai.wizard.repository.LevelTwoRepository;
import com.eai.wizard.repository.ViewLevelTwoRepository;
import com.eai.wizard.service.LevelOneService;
import com.eai.wizard.service.LevelTwoService;
import com.eai.wizard.utils.UtilPage;

@Service
public class LevelTwoServiceImpl implements LevelTwoService {
	
	private UtilPage utilPage;
    private Page page;
    private Progress progress;
	
	@PersistenceContext
    private EntityManager manager;
	
	@Autowired
    private LevelTwoRepository levelTwoRepository;
	
	@Autowired
    private ViewLevelTwoRepository viewLevelTwoRepository;
	
	@Autowired
	LevelOneService levelOneService;
    
	public LevelTwoServiceImpl(){
		utilPage = new UtilPage();
        page = new Page();
        progress = Progress.getSingletonInstance();
    }
	
	
	@Override
    @Transactional
    public void save(LevelTwo levelTwo){
    	levelTwoRepository.save(levelTwo);
    }
	
    @Override
    @Transactional
    public void saveAll(List<LevelTwo> levelTwoList){
    	levelTwoRepository.saveAll(levelTwoList);
    }

	@Override
	@Transactional
	public int findPendingOrFail() {
		return viewLevelTwoRepository.findPendingOrFail();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ViewLevelTwo> findPendingOrFail(int from, int to) {
    	
    	StringBuilder sql = new StringBuilder();
    	sql
    		.append("SELECT * FROM view_level_two l WHERE l.status IN ('P','F') limit ")
    		.append(from)
    		.append(", ")
    		.append(to);
    	
    	Query query = manager.createNativeQuery(sql.toString(), ViewLevelTwo.class);
    	
		return query.getResultList();
	}
	
	@Override
	@Transactional
	public int findSuccess() {
		return viewLevelTwoRepository.findSuccess();
	}

	@Override
	@Transactional
	public LevelTwo findById(Integer idLevel2) {
		return levelTwoRepository.findById(idLevel2).orElse(null);
	}
	
	public void levelTwo(
    		LevelOne levelOne, 
    		String userAgent,
    		int numberOfRetries){
    	
		try {
			Element firstDiv = page.element(levelOne.getUrl(), "refinementList", numberOfRetries, userAgent);
			
    		if(firstDiv != null){
    			Elements articles = firstDiv.getElementsByClass("a-link-normal");
    			List<LevelTwo> levelTwoList = new ArrayList<>();
    			
    			 for(Element article : articles){
    				 data(article, levelTwoList, levelOne.getIdLevel1());
    			 }
    			     
    			 progress.setCountSuccess(progress.calculateCountSuccess());
    			 this.saveAll(levelTwoList);
    			 levelOne.setStatus(Constants.SUCCESS.val());            
    		}else {
    			progress.setCountFail(progress.calculateCountFail());
    			levelOne.setStatus(Constants.FAILURE.val());
    		}
		}catch (Exception ex) {
			progress.setCountFail(progress.calculateCountFail());
			levelOne.setStatus(Constants.ERROR.val());
    	}
    
		levelOneService.save(levelOne);
    }
    
    private void data(Element article, List<LevelTwo> levelTwoList, Integer idLevel2) {
    	Elements span = article.getElementsByTag("span");
    	
    	if(!span.isEmpty()) {
    		String category = span.get(0).text().replaceAll(Constants.REPLACE.val(), "").trim();
			 
            int amount = Integer.parseInt(span.get(1).text().replaceAll(Constants.REPLACE.val(), "").trim());
            
            StringBuilder url = new StringBuilder();
            url.append(Constants.BASEURL.val())
            .append(article.attr("href"));
            
            LevelTwo levelTwo = new LevelTwo(category, amount, url.toString(), Constants.PENDING.val(), idLevel2);
  	                 
            List<String> urlPages = utilPage.urlPage(levelTwo);
  	                 
            if(urlPages.size() == 1) {
            	levelTwoList.add(levelTwo);
            }else {
            	for (String urls : urlPages) {
            		levelTwoList.add(new LevelTwo(category, amount, urls, Constants.PENDING.val(), idLevel2));
            	}
            }
    	}	                 
    }
}