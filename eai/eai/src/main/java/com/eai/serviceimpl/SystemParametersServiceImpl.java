package com.eai.serviceimpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eai.dto.Pagination;
import com.eai.model.SystemParameters;
import com.eai.repository.SystemParametersRepository;
import com.eai.service.SystemParametersService;

@Service
public class SystemParametersServiceImpl implements SystemParametersService {
	
	@PersistenceContext
    private EntityManager manager;
	
	@Autowired
	SystemParametersRepository systemParametersRepository;
	
	@Override
	@Transactional
	public SystemParameters findById(Integer idSystemParameters) {
		return systemParametersRepository.findById(idSystemParameters).orElse(null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void findAll(Pagination pagination, Long pageSize) {
		StringBuilder sql = new StringBuilder();
		
    	sql.append("SELECT * FROM system_parameters s WHERE 1=1 ");
    	    	
    	Query query = manager.createNativeQuery(sql.toString(), SystemParameters.class);
    	List<SystemParameters> systemParametersList = query.getResultList();
    	
    	pagination.setResult(systemParametersList);
	}

	@Override
	@Transactional
	public SystemParameters saveOrUpdate(SystemParameters systemParameters) {
		return systemParametersRepository.save(systemParameters);
	}
}