package com.eai.service;

import com.eai.dto.MessageResponse;
import com.eai.dto.Pagination;
import com.eai.model.LogError;

public interface LogErrorService{
	public MessageResponse save(LogError logError);
	
	public void findAll(Pagination pagination, Long pageSize);
}