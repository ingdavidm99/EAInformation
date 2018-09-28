package com.eai.wizard.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eai.wizard.model.ViewLevelTwo;

@Repository
public interface ViewLevelTwoRepository extends CrudRepository<ViewLevelTwo, Integer> {
	
	@Query("SELECT COUNT(l.idLevel2) FROM ViewLevelTwo l WHERE l.status IN ('P','F')")
	public int findPendingOrFail();
	
	@Query("SELECT COUNT(l.idLevel2)FROM ViewLevelTwo l WHERE l.status IN ('S')")
	public int findSuccess();
}