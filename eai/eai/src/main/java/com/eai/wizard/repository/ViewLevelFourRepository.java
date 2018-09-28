package com.eai.wizard.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eai.wizard.model.ViewLevelFour;

@Repository
public interface ViewLevelFourRepository extends CrudRepository<ViewLevelFour, Integer> {
		
}