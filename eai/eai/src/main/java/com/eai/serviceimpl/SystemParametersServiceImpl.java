package com.eai.serviceimpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
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
		SystemParameters systemParameters = pagination.getSystemParameters();
		
    	sql.append("SELECT * FROM system_parameters s WHERE 1=1 ");
    	
    	if(systemParameters.getIdSystemParameters() != null) {
    		sql.append("and s.ID_SYSTEM_PARAMETERS = '")
    		   .append(systemParameters.getIdSystemParameters())
    		   .append("' ");
    	}
    	
    	if(!systemParameters.getName().equals("")) {
    		sql.append("and s.NAME = '")
    		   .append(systemParameters.getName())
    		   .append("' ");
    	}
    	
    	if(!systemParameters.getValue().equals("")) {
    		sql.append("and s.VALUE = '")
    		   .append(systemParameters.getValue())
    		   .append("' ");
    	}
    	
    	if(!systemParameters.getDescription().equals("")) {
    		sql.append("and s.DESCRIPTION = '")
    		   .append(systemParameters.getDescription())
    		   .append("' ");
    	}
    	
    	if(!systemParameters.getUserName().equals("")) {
    		sql.append("and s.USER_NAME = '")
    		   .append(systemParameters.getUserName())
    		   .append("' ");
    	}
    	
    	if(!systemParameters.getDate().equals("")) {
    		sql.append("and s.DATE = '")
    		   .append(systemParameters.getDate())
    		   .append("' ");
    	}
    	
    	Query query = manager.createNativeQuery(sql.toString(), SystemParameters.class);
    	List<SystemParameters> systemParametersList = query.getResultList();
    	
    	if(!systemParametersList.isEmpty()) {
    		pagination.getPage(query.getResultList().size(), pageSize);
        	
        	PagedListHolder<SystemParameters> pageList = new PagedListHolder<>(systemParametersList);
        	MutableSortDefinition x = new MutableSortDefinition ("name", true, true);
        	pageList.setSort(x);
        	pageList.resort();
        	
        	int page = (int)(pagination.getPage()-1);
        	pageList.setPageSize(pageSize.intValue());
        	pageList.setPage(page); 	
        	pagination.setSystemParametersList(pageList.getPageList());
    	}else {
    		pagination.setLength(0);
    		pagination.setPage(0);
    		pagination.setSize(0);
    	}
	}

	@Override
	@Transactional
	public SystemParameters saveOrUpdate(SystemParameters systemParameters) {
		return systemParametersRepository.save(systemParameters);
	}
}