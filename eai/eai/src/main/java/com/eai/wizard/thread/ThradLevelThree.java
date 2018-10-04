package com.eai.wizard.thread;

import java.util.List;

import com.eai.wizard.model.ViewLevelTwo;
import com.eai.wizard.service.LevelThreeService;

public class ThradLevelThree extends  Thread{
    
    private final List<ViewLevelTwo> viewLevelTwoList;
    private final LevelThreeService levelThreeService;
    private final String userAgent;
    private final int numberOfRetries;
    
    public ThradLevelThree(
    		List<ViewLevelTwo> viewLevelTwoList,
    		LevelThreeService levelThreeService,
    		String userAgent,
    		int numberOfRetries) {
    	
    	this.viewLevelTwoList = viewLevelTwoList;
        this.levelThreeService = levelThreeService;
        this.userAgent = userAgent;
        this.numberOfRetries = numberOfRetries;
        
        this.setDaemon(true);
    }
       
    @Override
    public void run(){
    	levelThreeService.levelThree(viewLevelTwoList, userAgent,numberOfRetries);
    }
}
