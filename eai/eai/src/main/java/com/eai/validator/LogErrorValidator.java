package com.eai.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.eai.dto.Pagination;

public class LogErrorValidator extends ParentValidator implements Validator{
	
	public LogErrorValidator(String local) {
		super(local);
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Pagination.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Pagination pagination = (Pagination) target;
	}
}