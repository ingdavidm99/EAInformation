package com.eai.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.eai.dto.Constants;
import com.eai.dto.Pagination;

public class SearchLogErrorValidator extends ParentValidator implements Validator{
	
	private enum Name {
		IDLOGERROR("data[0]"),
		USERNAME("data[2]"),
		DATE("data[4]");
		
		private String val;

		Name(String val) {
	        this.val = val;
	    }

	    public String val() {
	        return val;
	    }
	}
	
	public SearchLogErrorValidator(String local) {
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
			onlyContainNumber(Name.IDLOGERROR.val(), pagination.getData().get(0), errors);
		}
		
		if(!Constants.EMPTY.val().equals(pagination.getData().get(2))) {
			onlyContainLetters(Name.USERNAME.val(), pagination.getData().get(2), errors);
		}
		
		if(!Constants.EMPTY.val().equals(pagination.getData().get(4))) {
			incorrectDateFormat(Name.DATE.val(), pagination.getData().get(4), errors);
		}
	}
}