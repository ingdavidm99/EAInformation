package com.eai.wizard.levelthree;

import java.util.List;

import com.eai.wizard.model.ViewLevelTwo;
import com.eai.wizard.service.LevelThreeService;
import com.eai.wizard.service.LevelTwoService;

public class ThradLevelThree extends  Thread{
    private final PageLevelThree pageLevelThree;
    
    private final List<ViewLevelTwo> viewLevelTwoList;
    private final LevelTwoService levelTwoService;
    private final LevelThreeService levelThreeService;
    private final String userAgent;
    private final int numberOfRetries;
    
    public ThradLevelThree(
    		List<ViewLevelTwo> viewLevelTwoList,
    		LevelTwoService levelTwoService,
    		LevelThreeService levelThreeService,
    		String userAgent,
    		int numberOfRetries) {
    	
    	pageLevelThree = new PageLevelThree();
    	
    	this.viewLevelTwoList = viewLevelTwoList;
        this.levelTwoService = levelTwoService;
        this.levelThreeService = levelThreeService;
        this.userAgent = userAgent;
        this.numberOfRetries = numberOfRetries;
        
        this.setDaemon(true);
    }
       
    @Override
    public void run(){
    	pageLevelThree.levelThree(
    			viewLevelTwoList, 
    			levelTwoService, 
    			levelThreeService, 
    			userAgent,
    			numberOfRetries);
    }
}
