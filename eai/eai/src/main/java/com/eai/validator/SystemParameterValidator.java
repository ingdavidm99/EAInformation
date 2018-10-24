package com.eai.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.eai.model.SystemParameter;

public class SystemParameterValidator extends ParentValidator implements Validator{
	
	public SystemParameterValidator(String local) {
		super(local);
	}

	
	@Override
	public boolean supports(Class<?> clazz) {
		return SystemParameter.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
	}
}