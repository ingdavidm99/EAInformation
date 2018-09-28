package com.eai.wizard.levelone;

import com.eai.dto.Constants;
import com.eai.dto.Progress;
import com.eai.wizard.model.LevelOne;
import com.eai.wizard.service.LevelOneService;
import com.eai.wizard.utils.UtilPage;

public class PageLevelOne {
	
	private UtilPage utilPage;
	private Progress progress;
    
    public PageLevelOne(){
    	utilPage = new UtilPage();
        progress = Progress.getSingletonInstance();
    }

	public void levelOne(LevelOne levelOne, LevelOneService levelOneService){
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
        
        levelOneService.save(levelOne);
    }
}
