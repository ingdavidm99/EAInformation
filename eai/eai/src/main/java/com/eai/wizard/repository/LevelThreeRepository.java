package com.eai.wizard.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eai.wizard.model.LevelThree;

@Repository
public interface LevelThreeRepository extends CrudRepository<LevelThree, Integer> {
	
}