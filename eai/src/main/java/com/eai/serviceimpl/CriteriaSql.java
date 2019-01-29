package com.eai.serviceimpl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;

import com.eai.dto.Constants;

public class CriteriaSql {

	public void sqlEqual(CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Expression<?> root, Object value) {
		if(!Constants.EMPTY.val().equals(value) && !Constants.ZERO.val().equals(value)) {
			criteriaQuery.where(criteriaBuilder.equal(root, value));
		}	
	}
	
    public void sqlLike(CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Expression<String> root, Object value) {
    	if(!Constants.EMPTY.val().equals(value)) {
    		criteriaQuery.where(criteriaBuilder.like(root, "%" + value.toString() + "%"));
    	}	
    }
    
    public void sqlBool(CriteriaBuilder builder, CriteriaQuery<?> criteria, Expression<Boolean> root, Object value) {
    	if(!Constants.EMPTY.val().equals(value)) {
    		criteria.where(builder.isTrue(root));
    	}	
    }
}
