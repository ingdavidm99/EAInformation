package com.eai.wizard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eai.wizard.model.LevelOne;

@Repository
public interface LevelOneRepository extends CrudRepository<LevelOne, Integer> {
	
	@Query("FROM LevelOne l WHERE l.status IN ('P','F')")
	public List<LevelOne> findPendingOrFail();
	
	@Query("SELECT COUNT(l.status) FROM LevelOne l WHERE l.status IN ('S')")
	public int findSuccess();
}