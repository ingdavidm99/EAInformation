package com.eai.service;

import com.eai.dto.Pagination;
import com.eai.model.Rule;

public interface RuleService{
	public Rule findById(Integer idRule);
	
	public void findAll(Pagination pagination, Long pageSize);
	
	public void saveOrUpdate(Rule rule);
}