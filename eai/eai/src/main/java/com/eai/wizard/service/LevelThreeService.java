package com.eai.wizard.service;

import java.util.List;

import com.eai.wizard.model.LevelThree;
import com.eai.wizard.model.ViewLevelThree;
import com.eai.wizard.model.ViewLevelTwo;

public interface LevelThreeService{
	
	public void save(LevelThree levelThree);
	
	public void saveAll(List<LevelThree> levelThreeList);	
	
	public List<ViewLevelThree> findPendingOrFail();
	
	public int findSuccess();
	
	public LevelThree findById(Integer idLevel3);
	
	public void levelThree(
			List<ViewLevelTwo> viewLevelTwoList, 
			String userAgent,
			int numberOfRetries);
}