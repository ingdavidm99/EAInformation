package com.eai.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eai.model.Menu;

@Repository
public interface MenuRepository extends CrudRepository<Menu, Integer> {
	
	@Query("from Menu m where m.parentMenu.idParentMenu = :idParentMenu and m.role.idRole = :idRole order by m.order")
	public List<Menu> menuList(@Param("idParentMenu") Integer idParentMenu, @Param("idRole") Integer idRole);
	
	@Query("from Menu m where m.urlName = :urlName")
	public Menu findByUrlName(@Param("urlName") String urlName);
	
}