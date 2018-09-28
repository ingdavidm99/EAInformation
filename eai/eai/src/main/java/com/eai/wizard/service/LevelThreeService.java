package com.eai.wizard.service;

import java.util.List;

import com.eai.wizard.model.LevelThree;
import com.eai.wizard.model.ViewLevelThree;

public interface LevelThreeService{
	
	public void save(LevelThree levelThree);
	
	public void save(List<LevelThree> levelThreeList);	
	
	public List<ViewLevelThree> findPendingOrFail();
	
	public int findSuccess();
	
	public LevelThree findOne(Integer idLevel3);
}