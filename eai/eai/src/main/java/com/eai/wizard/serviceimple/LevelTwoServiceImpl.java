package com.eai.wizard.serviceimple;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eai.wizard.model.LevelTwo;
import com.eai.wizard.model.ViewLevelTwo;
import com.eai.wizard.repository.LevelTwoRepository;
import com.eai.wizard.repository.ViewLevelTwoRepository;
import com.eai.wizard.service.LevelTwoService;

@Service
public class LevelTwoServiceImpl implements LevelTwoService {
	
	@PersistenceContext
    private EntityManager manager;
	
	@Autowired
    private LevelTwoRepository levelTwoRepository;
	
	@Autowired
    private ViewLevelTwoRepository viewLevelTwoRepository;
    
	@Override
    @Transactional
    public void save(LevelTwo levelTwo){
    	levelTwoRepository.save(levelTwo);
    }
	
    @Override
    @Transactional
    public void save(List<LevelTwo> levelTwoList){
    	levelTwoRepository.saveAll(levelTwoList);
    }

	@Override
	@Transactional
	public int findPendingOrFail() {
		return viewLevelTwoRepository.findPendingOrFail();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ViewLevelTwo> findPendingOrFail(int from, int to) {
    	
    	StringBuilder sql = new StringBuilder();
    	sql
    		.append("SELECT * FROM view_level_two l WHERE l.status IN ('P','F') limit ")
    		.append(from)
    		.append(", ")
    		.append(to);
    	
    	Query query = manager.createNativeQuery(sql.toString(), ViewLevelTwo.class);
    	
		return query.getResultList();
	}
	
	@Override
	@Transactional
	public int findSuccess() {
		return viewLevelTwoRepository.findSuccess();
	}

	@Override
	@Transactional
	public LevelTwo findOne(Integer idLevel2) {
		return levelTwoRepository.findById(idLevel2).orElse(null);
	}
}