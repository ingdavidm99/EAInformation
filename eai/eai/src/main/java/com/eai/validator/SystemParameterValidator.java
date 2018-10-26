package com.eai.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.eai.dto.Constants;
import com.eai.dto.Pagination;

public class SystemParameterValidator extends ParentValidator implements Validator{
	
	private enum Name {
		IDSYSTEMPARAMETER("data[0]"),
		NAME("data[1]"),
		DESCRIPTION("data[3]"),
		DATE("data[6]");
		
		private String val;

		Name(String val) {
	        this.val = val;
	    }

	    public String val() {
	        return val;
	    }
	}
	
	public SystemParameterValidator(String local) {
		super(local);
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Pagination.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Pagination pagination = (Pagination) target;
		
		if(!Constants.EMPTY.val().equals(pagination.getData().get(0))) {
			onlyContainNumber(Name.IDSYSTEMPARAMETER.val(), pagination.getData().get(0), errors);
		}
		
		if(!Constants.EMPTY.val().equals(pagination.getData().get(1))) {
			onlyContainLetters(Name.NAME.val(), pagination.getData().get(1), errors);
		}
		
		if(!Constants.EMPTY.val().equals(pagination.getData().get(3))) {
			onlyContainLetters(Name.DESCRIPTION.val(), pagination.getData().get(3), errors);
		}
		
		if(!Constants.EMPTY.val().equals(pagination.getData().get(6))) {
			incorrectDateFormat(Name.DATE.val(), pagination.getData().get(6), errors);
		}
	}
}