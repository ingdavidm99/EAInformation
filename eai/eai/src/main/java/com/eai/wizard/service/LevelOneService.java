package com.eai.wizard.service;

import java.util.List;

import com.eai.wizard.model.LevelOne;

public interface LevelOneService{

	public void save(LevelOne levelOne);	
	
	public List<LevelOne> findPendingOrFail();
	
	public int findSuccess();
	
	public void levelOne(LevelOne levelOne);
}