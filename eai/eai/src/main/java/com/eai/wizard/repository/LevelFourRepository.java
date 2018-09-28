package com.eai.wizard.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eai.wizard.model.LevelFour;

@Repository
public interface LevelFourRepository extends CrudRepository<LevelFour, Integer> {

}