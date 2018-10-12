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
public class SystemParametersServiceImpl extends SqlImplement implements SystemParametersService {
	
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
		
    	sql.append("SELECT * FROM system_parameters WHERE 1=1 ");
    	
    	sqlAnd(sql, pagination.getData().get(0), "ID_SYSTEM_PARAMETERS");
    	
    	sqlAnd(sql, pagination.getData().get(1), "NAME");
    	
    	sqlAnd(sql, pagination.getData().get(2), "VALUE");
    	
    	sqlAnd(sql, pagination.getData().get(3), "DESCRIPTION");
    	
    	sqlAnd(sql, pagination.getData().get(4), "TYPE");
    	
    	sqlAnd(sql, pagination.getData().get(5), "USER_NAME");
    	
    	sqlAnd(sql, pagination.getData().get(6), "DATE");
    	
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