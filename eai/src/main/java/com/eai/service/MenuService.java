package com.eai.service;

import java.util.List;

import com.eai.model.Menu;

public interface MenuService{

	public List<Menu> menuList(Integer idParentMenu, Integer idRole);
	
	public Menu findByUrlName(String urlName);
}