package com.eai.service;

import com.eai.dto.Pagination;
import com.eai.model.SystemParameter;

public interface SystemParametersService{

	public SystemParameter findById(Integer idSystemParameter);
	
	public void findAll(Pagination pagination, Long pageSize);
	
	public SystemParameter saveOrUpdate(SystemParameter systemParameterOld);
}