package com.eai.wizard.leveltwo;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.eai.dto.Constants;
import com.eai.dto.Progress;
import com.eai.wizard.model.LevelOne;
import com.eai.wizard.model.LevelTwo;
import com.eai.wizard.page.Page;
import com.eai.wizard.service.LevelOneService;
import com.eai.wizard.service.LevelTwoService;
import com.eai.wizard.utils.UtilPage;

public class PageLevelTwo{
    private UtilPage utilPage;
    private Page page;
    private Progress progress;
    
    public PageLevelTwo(){
    	utilPage = new UtilPage();
        page = new Page();
        progress = Progress.getSingletonInstance();
    }
    
    public void levelTwo(
    		LevelOne levelOne, 
    		LevelOneService levelOneService, 
    		LevelTwoService levelTwoService, 
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
    			     
    			 levelTwoService.save(levelTwoList);
    			 levelOne.setStatus(Constants.SUCCESS.val());            
    			 progress.setCountSuccess(progress.calculateCountSuccess());
    		}else {
    			levelOne.setStatus(Constants.FAILURE.val());
    			progress.setCountFail(progress.calculateCountFail());
    		}
		}catch (Exception ex) {
			progress.setCountFail(progress.calculateCountFail());
    		levelOne.setStatus(Constants.ERROR.val());
    	}
    
		levelOneService.save(levelOne);
    }
    
    private void data(Element article, List<LevelTwo> levelTwoList, Integer id) {
    	Elements span = article.getElementsByTag("span");
    	if(!span.isEmpty()) {
    		String category = span.get(0).text().replaceAll(Constants.REPLACE.val(), "").trim();
			 
            int amount = Integer.parseInt(span.get(1).text().replaceAll(Constants.REPLACE.val(), "").trim());
            
            StringBuilder url = new StringBuilder();
            url.append(Constants.BASEURL.val())
            .append(article.attr("href"));
            
            LevelTwo levelTwo = new LevelTwo(category, amount, url.toString(), Constants.PENDING.val(), id);
  	                 
            List<String> urlPages = utilPage.urlPage(levelTwo);
  	                 
            if(urlPages.size() == 1) {
            	levelTwoList.add(levelTwo);
            }else {
            	for (String urls : urlPages) {
            		levelTwoList.add(new LevelTwo(category, amount, urls, Constants.PENDING.val(), id));
            	}
            }
    	}	                 
    }
}
