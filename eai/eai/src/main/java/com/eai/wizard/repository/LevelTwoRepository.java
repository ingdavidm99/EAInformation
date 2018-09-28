package com.eai.wizard.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eai.wizard.model.LevelTwo;

@Repository
public interface LevelTwoRepository extends CrudRepository<LevelTwo, Integer> {

}