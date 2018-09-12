package com.eai.serviceimpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eai.dto.Constants;
import com.eai.dto.MessageResponse;
import com.eai.model.LogError;
import com.eai.repository.LogErrorRepository;
import com.eai.service.LogErrorService;
import com.eai.service.SystemParametersService;

@Service
public class LogErrorServiceImpl implements LogErrorService {
	
	@PersistenceContext
    private EntityManager manager;
	
	@Autowired
	LogErrorRepository logErrorRepository;
	
	@Autowired
	SystemParametersService systemParametersService;
		
	@Override
    @Transactional
    public MessageResponse save(LogError logError){
		Integer idLogError = logErrorRepository.save(logError).getIdLogError();
		
		MessageResponse message = new MessageResponse();
		message.setStatus(Constants.ERROR.val());
		message.setCode(idLogError);
		
		return message;
    }	
}