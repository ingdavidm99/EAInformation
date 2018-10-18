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

import com.eai.dto.Constants;
import com.eai.dto.MessageResponse;
import com.eai.dto.Pagination;
import com.eai.model.LogError;
import com.eai.repository.LogErrorRepository;
import com.eai.service.LogErrorService;
import com.eai.service.SystemParametersService;

@Service
public class LogErrorServiceImpl extends SqlImplement implements LogErrorService {
	
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
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void findAll(Pagination pagination, Long pageSize) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
    	CriteriaQuery<LogError> criteria = builder.createQuery(LogError.class);
    	Root<LogError> root = criteria.from(LogError.class);
		
    	sqlEqual(builder, criteria, root.get("idLogError"), pagination.getData().get(0));
    	   	
    	sqlLike(builder, criteria, root.get("error"), pagination.getData().get(1));
    	
    	sqlEqual(builder, criteria, root.get("userName"), pagination.getData().get(2));
    	
    	sqlEqual(builder, criteria, root.get("path"), pagination.getData().get(3));
    	
    	sqlEqual(builder, criteria, root.get("date"), pagination.getData().get(4));
    	
    	Query query = manager.createQuery(criteria);
    	List<LogError> logErrorList = query.getResultList();
    	
    	if(!logErrorList.isEmpty()) {
	    	pagination.getPage(query.getResultList().size(), pageSize);
	    	
	    	PagedListHolder<LogError> pageList = new PagedListHolder<>(logErrorList);
	    	MutableSortDefinition x = new MutableSortDefinition ("date", true, false);
	    	pageList.setSort(x);
	    	pageList.resort();
	    	
	    	int page = (int)(pagination.getPage()-1);
        	pageList.setPageSize(pageSize.intValue());
        	pageList.setPage(page); 	
	    	pagination.setLogErrorList(pageList.getPageList());
    	}else {
    		pagination.setLength(0);
    		pagination.setPage(0);
    		pagination.setSize(0);
    	}
	}
}