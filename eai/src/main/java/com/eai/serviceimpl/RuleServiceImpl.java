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
import com.eai.model.Rule;
import com.eai.repository.RuleRepository;
import com.eai.service.RuleService;

@Service
public class RuleServiceImpl extends CriteriaSql implements RuleService {
	
	@PersistenceContext
    private EntityManager manager;
	
	@Autowired
	RuleRepository ruleRepository;
	
	@Override
	@Transactional
	public Rule findById(Integer idRule) {
		return ruleRepository.findById(idRule).orElse(null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void findAll(Pagination pagination, Long pageSize) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
    	CriteriaQuery<Rule> criteriaQuery = criteriaBuilder.createQuery(Rule.class);
    	Root<Rule> root = criteriaQuery.from(Rule.class);
    	
    	sqlEqual(criteriaBuilder, criteriaQuery, root.get("idRule"), pagination.getData().get(0));
    	
    	sqlLike(criteriaBuilder, criteriaQuery, root.get("code"), pagination.getData().get(1));
    	
    	sqlEqual(criteriaBuilder, criteriaQuery, root.get("status"), pagination.getData().get(2));

    	sqlLike(criteriaBuilder, criteriaQuery, root.get("description"), pagination.getData().get(3));
    	
    	sqlEqual(criteriaBuilder, criteriaQuery, root.get("date"), pagination.getData().get(4));
    	
    	sqlLike(criteriaBuilder, criteriaQuery, root.get("baseUrl"), pagination.getData().get(5));
    	
    	Query query = manager.createQuery(criteriaQuery);
    	List<Rule> ruleList = query.getResultList();
    	
    	if(!ruleList.isEmpty()) {
	    	pagination.getPage(query.getResultList().size(), pageSize);
	    	
	    	PagedListHolder<Rule> pageList = new PagedListHolder<>(ruleList);
	    	MutableSortDefinition x = new MutableSortDefinition ("idRule", true, false);
	    	pageList.setSort(x);
	    	pageList.resort();
	    	
	    	int page = (int)(pagination.getPage()-1);
        	pageList.setPageSize(pageSize.intValue());
        	pageList.setPage(page); 	
	    	pagination.setRuleList(pageList.getPageList());
    	}else {
    		pagination.setLength(0);
    		pagination.setPage(0);
    		pagination.setSize(0);
    	}
	}

	@Override
	@Transactional
	public void saveOrUpdate(Rule rule) {
		ruleRepository.save(rule);
	}
}