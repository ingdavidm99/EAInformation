package com.eai.service;

import com.eai.dto.Pagination;
import com.eai.model.SystemParameters;

public interface SystemParametersService{

	public SystemParameters findById(Integer idSystemParameters);
	
	public void findAll(Pagination pagination, Long pageSize);
	
	public SystemParameters saveOrUpdate(SystemParameters systemParameters);
}