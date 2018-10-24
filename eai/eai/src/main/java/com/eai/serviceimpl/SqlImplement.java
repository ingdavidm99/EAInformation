package com.eai.serviceimpl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;

import com.eai.dto.Constants;

public class SqlImplement {

	public void sqlEqual(CriteriaBuilder builder, CriteriaQuery<?> criteria, Expression<?> root, Object value) {
		if(!Constants.EMPTY.val().equals(value)) {
			criteria.where(builder.equal(root, value));
		}	
	}
	
    public void sqlLike(CriteriaBuilder builder, CriteriaQuery<?> criteria, Expression<String> root, Object value) {
    	if(!Constants.EMPTY.val().equals(value)) {
    		criteria.where(builder.like(root, value.toString()));
    	}	
    }
    
    public void sqlBool(CriteriaBuilder builder, CriteriaQuery<?> criteria, Expression<Boolean> root, Object value) {
    	if(!Constants.EMPTY.val().equals(value)) {
    		criteria.where(builder.isTrue(root));
    	}	
    }
}
