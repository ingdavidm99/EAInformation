package com.eai.wizard.thread;

import com.eai.wizard.model.LevelOne;
import com.eai.wizard.service.LevelTwoService;

public class ThradLevelTwo extends  Thread{
    private final LevelOne levelOne;
    private final LevelTwoService levelTwoService;
    private final String userAgent;
    private final int numberOfRetries;
    
    public ThradLevelTwo(
    		LevelOne levelOne,
    		LevelTwoService levelTwoService,
    		String userAgent,
    		int numberOfRetries) {
    	
    	this.levelOne = levelOne;
    	this.levelTwoService = levelTwoService;
    	this.userAgent = userAgent;
    	this.numberOfRetries = numberOfRetries;
    	
    	this.setDaemon(true);
    }
       
    @Override
    public void run(){
    	levelTwoService.levelTwo(
    			levelOne, 
    			userAgent,
    			numberOfRetries);
    }
}
