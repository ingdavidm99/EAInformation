package com.eai.wizard.leveltwo;

import com.eai.wizard.model.LevelOne;
import com.eai.wizard.service.LevelOneService;
import com.eai.wizard.service.LevelTwoService;

public class ThradLevelTwo extends  Thread{
    private final PageLevelTwo pageLevelTwo;
    
    private final LevelOne levelOne;
    private final LevelOneService levelOneService;
    private final LevelTwoService levelTwoService;
    private final String userAgent;
    private final int numberOfRetries;
    
    public ThradLevelTwo(
    		LevelOne levelOne,
    		LevelOneService levelOneService,
    		LevelTwoService levelTwoService,
    		String userAgent,
    		int numberOfRetries) {
    	
    	pageLevelTwo = new PageLevelTwo();    	
    	
    	this.levelOne = levelOne;
    	this.levelOneService = levelOneService;
    	this.levelTwoService = levelTwoService;
    	this.userAgent = userAgent;
    	this.numberOfRetries = numberOfRetries;
    	
    	this.setDaemon(true);
    }
       
    @Override
    public void run(){
    	pageLevelTwo.levelTwo(
    			levelOne, 
    			levelOneService, 
    			levelTwoService, 
    			userAgent,
    			numberOfRetries);
    }
}
