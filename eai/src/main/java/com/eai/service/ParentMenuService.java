package com.eai.service;

import java.util.List;

import com.eai.model.ParentMenu;

public interface ParentMenuService{

	public List<ParentMenu> parentMenuList(Integer idRole);
}