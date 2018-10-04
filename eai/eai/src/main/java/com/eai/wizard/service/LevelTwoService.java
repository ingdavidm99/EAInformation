package com.eai.wizard.service;

import java.util.List;

import com.eai.wizard.model.LevelOne;
import com.eai.wizard.model.LevelTwo;
import com.eai.wizard.model.ViewLevelTwo;

public interface LevelTwoService{
	
	public void save(LevelTwo levelTwo);
	
	public void saveAll(List<LevelTwo> levelTwoList);	
	
	public int findPendingOrFail();
	
	public List<ViewLevelTwo> findPendingOrFail(int limit, int limit1);
	
	public int findSuccess();
	
	public LevelTwo findById(Integer idLevel2);
	
	public void levelTwo(
    		LevelOne levelOne, 
    		String userAgent,
    		int numberOfRetries);
}