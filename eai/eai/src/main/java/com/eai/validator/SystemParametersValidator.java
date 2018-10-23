package com.eai.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.eai.model.SystemParameter;

public class SystemParametersValidator extends ParentValidator implements Validator{
	
	private enum Name {
		VALUE("value"),
		DESCRIPTION("description");
		
		private String val;

		Name(String val) {
	        this.val = val;
	    }

	    public String val() {
	        return val;
	    }
	}
	
	public SystemParametersValidator(String local) {
		super(local);
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return SystemParameter.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		SystemParameter systemParameter = (SystemParameter) target;
		
		String requiredField = getTransactionPage().get("requiredField");
			
		//value
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, Name.VALUE.val(), requiredField, requiredField);
		if (!errors.hasFieldErrors(Name.VALUE.val())) {
			
			switch (systemParameter.getType()) {
				case 1:
					onlyContainLetters(Name.VALUE.val(), systemParameter.getValue(), errors);
				break;
				case 2:
					onlyContainNumber(Name.VALUE.val(), systemParameter.getValue(), errors);
				break;
				default:
					
				break;
			}
		}
		
		//description
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, Name.DESCRIPTION.val(), requiredField, requiredField);
	}
}
