package com.eai.wizard.serviceimple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eai.wizard.model.LevelOne;
import com.eai.wizard.repository.LevelOneRepository;
import com.eai.wizard.service.LevelOneService;

@Service
public class LevelOneServiceImpl implements LevelOneService {
	
	@Autowired
	LevelOneRepository levelOneRepository;
	
	@Override
    @Transactional
    public void save(LevelOne levelOne){
		levelOneRepository.save(levelOne);
    }

	@Override
	@Transactional
	public List<LevelOne> findPendingOrFail() {
		return levelOneRepository.findPendingOrFail();
	}

	@Override
	@Transactional
	public int findSuccess() {
		return levelOneRepository.findSuccess();
	}
}