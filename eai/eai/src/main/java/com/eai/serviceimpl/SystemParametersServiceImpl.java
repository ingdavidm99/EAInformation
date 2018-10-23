package com.eai.serviceimpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eai.dto.Pagination;
import com.eai.model.SystemParameter;
import com.eai.repository.SystemParameterRepository;
import com.eai.service.SystemParametersService;

@Service
public class SystemParametersServiceImpl extends SqlImplement implements SystemParametersService {
	
	@PersistenceContext
    private EntityManager manager;
	
	@Autowired
	SystemParameterRepository systemParameterRepository;
	
	@Override
	@Transactional
	public SystemParameter findById(Integer idSystemParameters) {
		return systemParameterRepository.findById(idSystemParameters).orElse(null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void findAll(Pagination pagination, Long pageSize) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
    	CriteriaQuery<SystemParameter> criteria = builder.createQuery(SystemParameter.class);
    	Root<SystemParameter> root = criteria.from(SystemParameter.class);
    	
    	sqlEqual(builder, criteria, root.get("idSystemParameter"), pagination.getData().get(0));
    	
    	sqlLike(builder, criteria, root.get("name"), pagination.getData().get(1));
    	
    	sqlLike(builder, criteria, root.get("value"), pagination.getData().get(2));
    	
    	sqlLike(builder, criteria, root.get("description"), pagination.getData().get(3));
    	
    	sqlEqual(builder, criteria, root.get("type"), pagination.getData().get(4));
    	
    	sqlLike(builder, criteria, root.get("userName"), pagination.getData().get(5));
    	
    	sqlEqual(builder, criteria, root.get("date"), pagination.getData().get(6));
    	
    	Query query = manager.createQuery(criteria);
    	List<SystemParameter> systemParametersList = query.getResultList();
    	
    	if(!systemParametersList.isEmpty()) {
    		pagination.getPage(query.getResultList().size(), pageSize);
        	
        	PagedListHolder<SystemParameter> pageList = new PagedListHolder<>(systemParametersList);
        	MutableSortDefinition x = new MutableSortDefinition ("name", true, true);
        	pageList.setSort(x);
        	pageList.resort();
        	
        	int page = (int)(pagination.getPage()-1);
        	pageList.setPageSize(pageSize.intValue());
        	pageList.setPage(page); 	
        	pagination.setSystemParameterList(pageList.getPageList());
    	}else {
    		pagination.setLength(0);
    		pagination.setPage(0);
    		pagination.setSize(0);
    	}
	}

	@Override
	@Transactional
	public SystemParameter saveOrUpdate(SystemParameter systemParametersOld) {
		SystemParameter systemParametersNew = this.findById(systemParametersOld.getIdSystemParameter());
		
		systemParametersNew.setValue(systemParametersOld.getValue());
		systemParametersNew.setDescription(systemParametersOld.getDescription());
		
		return systemParameterRepository.save(systemParametersNew);
	}	
}