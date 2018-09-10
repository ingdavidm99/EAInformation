package com.eai.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eai.model.ParentMenu;

@Repository
public interface ParentMenuRepository extends CrudRepository<ParentMenu, Integer> {
	
	@Query("from ParentMenu p where p.role.idRole = :idRole order by p.order")
	public List<ParentMenu> parentMenuList(@Param("idRole") Integer idRole);
}