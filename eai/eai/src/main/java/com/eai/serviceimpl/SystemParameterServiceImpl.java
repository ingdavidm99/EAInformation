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
import com.eai.service.SystemParameterService;

@Service
public class SystemParameterServiceImpl extends SqlImplement implements SystemParameterService {
	
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
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
    	CriteriaQuery<SystemParameter> criteriaQuery = criteriaBuilder.createQuery(SystemParameter.class);
    	Root<SystemParameter> root = criteriaQuery.from(SystemParameter.class);
    	
    	sqlEqual(criteriaBuilder, criteriaQuery, root.get("idSystemParameter"), pagination.getData().get(0));
    	
    	sqlLike(criteriaBuilder, criteriaQuery, root.get("name"), pagination.getData().get(1));
    	
    	sqlLike(criteriaBuilder, criteriaQuery, root.get("value"), pagination.getData().get(2));
    	
    	sqlLike(criteriaBuilder, criteriaQuery, root.get("description"), pagination.getData().get(3));
    	
    	sqlEqual(criteriaBuilder, criteriaQuery, root.get("type"), pagination.getData().get(4));
    	
    	sqlLike(criteriaBuilder, criteriaQuery, root.get("userName"), pagination.getData().get(5));
    	
    	sqlEqual(criteriaBuilder, criteriaQuery, root.get("date"), pagination.getData().get(6));
    	
    	Query query = manager.createQuery(criteriaQuery);
    	List<SystemParameter> systemParameterList = query.getResultList();
    	
    	if(!systemParameterList.isEmpty()) {
    		pagination.getPage(query.getResultList().size(), pageSize);
        	
        	PagedListHolder<SystemParameter> pageList = new PagedListHolder<>(systemParameterList);
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