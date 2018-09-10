package com.eai.service;

import com.eai.dto.MessageResponse;
import com.eai.model.LogError;

public interface LogErrorService{
	public MessageResponse save(LogError logError);
}