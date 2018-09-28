package com.eai.wizard.serviceimple;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eai.wizard.model.LevelThree;
import com.eai.wizard.model.ViewLevelThree;
import com.eai.wizard.repository.LevelThreeRepository;
import com.eai.wizard.repository.ViewLevelThreeRepository;
import com.eai.wizard.service.LevelThreeService;

@Service
public class LevelThreeServiceImpl implements LevelThreeService {
	
	@PersistenceContext
    private EntityManager manager;
	
	@Autowired
    private LevelThreeRepository levelThreeRepository;
	
	@Autowired
    private ViewLevelThreeRepository viewLevelThreeRepository;
    
	@Override
    @Transactional
    public void save(LevelThree levelThree){
		levelThreeRepository.save(levelThree);
    }
	
    @Override
    @Transactional
    public void save(List<LevelThree> levelThreeList){
    	levelThreeRepository.saveAll(levelThreeList);
    }
    
    @SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ViewLevelThree> findPendingOrFail() {
    	
    	StringBuilder sql = new StringBuilder();
    	sql.append("SELECT * FROM view_level_three l WHERE l.status IN ('P','F')");
    	
    	Query query = manager.createNativeQuery(sql.toString(), ViewLevelThree.class);
    	
		return query.getResultList();
	}
	
	@Override
	@Transactional
	public int findSuccess() {
		return viewLevelThreeRepository.findSuccess();
	}
	
	@Override
	@Transactional
	public LevelThree findOne(Integer idLevel3) {
		return levelThreeRepository.findById(idLevel3).orElse(null);
	}
}