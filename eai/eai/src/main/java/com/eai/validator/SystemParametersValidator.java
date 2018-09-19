package com.eai.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.eai.dto.Constants;
import com.eai.dto.TransactionPage;
import com.eai.model.SystemParameters;

public class SystemParametersValidator implements Validator{
	
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
	
	@Override
	public boolean supports(Class<?> clazz) {
		return SystemParameters.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		SystemParameters systemParameters = (SystemParameters) target;
		TransactionPage transactionPage = new TransactionPage();
		
		String requiredField = transactionPage.get("requiredField");
		String onlyContainLetters = transactionPage.get("onlyContainLetters");
		String onlyContainNumber = transactionPage.get("onlyContainNumber");
			
		//value
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, Name.VALUE.val(), requiredField, requiredField);
		if (!errors.hasFieldErrors(Name.VALUE.val())) {
			
			String regex;
			String regexMessages = null;
			switch (systemParameters.getType()) {
				case 1:
					regex = Constants.STRING_PATTERN.val();
					regexMessages = onlyContainLetters;
				break;
				case 2:
					regex = Constants.NUMBER_PATTERN.val();
					regexMessages = onlyContainNumber;
				break;
				default:
					regex = null;
				break;
			}
			
			if(regex != null) {
				Pattern pattern = Pattern.compile(regex);  
				Matcher matcher = pattern.matcher(systemParameters.getValue());
				
				if (!matcher.matches()) {  
					errors.rejectValue(Name.VALUE.val(), regexMessages, regexMessages);  
				} 
			}
		}
		
		//description
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, Name.DESCRIPTION.val(), requiredField, requiredField);
	}
}
