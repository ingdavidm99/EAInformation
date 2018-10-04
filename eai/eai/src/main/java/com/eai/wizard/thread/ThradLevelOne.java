package com.eai.wizard.thread;

import com.eai.wizard.model.LevelOne;
import com.eai.wizard.service.LevelOneService;

public class ThradLevelOne extends  Thread{
	private final LevelOne levelOne;
	private final LevelOneService levelOneService;
    

    public ThradLevelOne(LevelOne levelOne, LevelOneService levelOneService) {
        
        this.levelOne = levelOne;
    	this.levelOneService = levelOneService;
    	this.setDaemon(true);
    }
       
    @Override
    public void run(){ 
    	levelOneService.levelOne(levelOne);
    }
}
