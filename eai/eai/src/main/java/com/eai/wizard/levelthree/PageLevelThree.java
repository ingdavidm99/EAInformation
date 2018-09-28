package com.eai.wizard.levelthree;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.eai.dto.Constants;
import com.eai.dto.Progress;
import com.eai.wizard.model.LevelThree;
import com.eai.wizard.model.LevelTwo;
import com.eai.wizard.model.ViewLevelTwo;
import com.eai.wizard.page.Page;
import com.eai.wizard.service.LevelThreeService;
import com.eai.wizard.service.LevelTwoService;

public class PageLevelThree{
    private Page page;
    private Progress progress;
    
    public PageLevelThree(){
    	page = new Page();
        progress = Progress.getSingletonInstance();
    }
    
    public void levelThree(
    		List<ViewLevelTwo> viewLevelTwoList, 
    		LevelTwoService levelTwoService, 
    		LevelThreeService levelThreeService, 
    		String userAgent,
    		int numberOfRetries){
    	
    	for(ViewLevelTwo viewLevelTwo : viewLevelTwoList){
    		LevelTwo levelTwo = levelTwoService.findOne(viewLevelTwo.getIdLevel2());
    		
    		try {
    			Element firstDiv = page.element(viewLevelTwo.getUrl(), "rightResultsATF", numberOfRetries, userAgent);
	    		List<LevelThree> levelThreeList = new ArrayList<>();
	    		
	    		if(firstDiv != null){
	    			data(firstDiv, levelThreeList, viewLevelTwo.getIdLevel2());
	    		}
	    		
	    		if(!levelThreeList.isEmpty()){
	    			progress.setCountSuccess(progress.calculateCountSuccess());
	    			levelThreeService.save(levelThreeList);
	    			levelTwo.setStatus(Constants.SUCCESS.val());
	    		}else {
	    			progress.setCountFail(progress.calculateCountFail());
	    			levelTwo.setStatus(Constants.FAILURE.val());
	    		}
    		}catch (Exception ex) {
    			progress.setCountFail(progress.calculateCountFail());
    			levelTwo.setStatus(Constants.ERROR.val());
        	}
    		
    		levelTwoService.save(levelTwo);
    	}
    }
    
    private void data(Element firstDiv, List<LevelThree> levelThreeList, Integer id) {
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
      		
      		if(!name.toString().isEmpty() && !url.toString().isEmpty())
      			levelThreeList.add(
						new LevelThree(
								name.toString(), 
								price.toString(), 
								attachment.toString(), 
								url.toString(), 
								Constants.PENDING.val(), 
								id
						));
          	}
		}  
    }
}
