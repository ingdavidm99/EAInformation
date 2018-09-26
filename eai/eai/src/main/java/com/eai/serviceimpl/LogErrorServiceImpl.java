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

import com.eai.dto.Constants;
import com.eai.dto.MessageResponse;
import com.eai.dto.Pagination;
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
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void findAll(Pagination pagination, Long pageSize) {
		StringBuilder sql = new StringBuilder();
		LogError logError = pagination.getLogError();
		
    	sql.append("SELECT * FROM log_error l WHERE 1=1 ");
    	
    	if(logError.getIdLogError() != null) {
    		sql.append("and l.ID_LOG_ERROR = '")
    		   .append(logError.getIdLogError())
    		   .append("' ");
    	}
    	
    	if(logError.getError() != null) {
    		sql.append("and l.ERROR LIKE '%")
    		   .append(logError.getError())
    		   .append("%' ");
    	}
    	
    	if(!logError.getUserName().equals("")) {
    		sql.append("and l.USER_NAME = '")
    		   .append(logError.getUserName())
    		   .append("' ");
    	}
    	
    	if(!logError.getPath().equals("")) {
    		sql.append("and l.PATH = '")
    		   .append(logError.getPath())
    		   .append("' ");
    	}
    	
    	if(!logError.getDate().equals("")) {
    		sql.append("and l.DATE = '")
    		   .append(logError.getDate())
    		   .append("' ");
    	}
    	
    	Query query = manager.createNativeQuery(sql.toString(), LogError.class);
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