package com.eai.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;

import com.eai.dto.Constants;
import com.eai.dto.TransactionPage;

public class ParentValidator {
	
	private TransactionPage transactionPage;

    public ParentValidator (String locale) {
    	transactionPage = new TransactionPage(locale);
    }
   
    public void onlyContainLetters(String element, Object value,Errors errors) {
    	if (!errors.hasFieldErrors(element)) {
			Pattern pattern = Pattern.compile(Constants.NAME_PATTERN.val());  
			Matcher matcher = pattern.matcher(value.toString());
			
			if (!matcher.matches()) { 
				String onlyContainLetters = transactionPage.get("onlyContainLetters");
				errors.rejectValue(element, onlyContainLetters, onlyContainLetters);  
			} 
		}
    }
    
    public void onlyContainNumber(String element, Object value,Errors errors) {
    	if (!errors.hasFieldErrors(element)) {
			Pattern pattern = Pattern.compile(Constants.NUMBER_PATTERN.val());  
			Matcher matcher = pattern.matcher(value.toString());
			
			if (!matcher.matches()) { 
				String onlyContainLetters = transactionPage.get("onlyContainNumber");
				errors.rejectValue(element, onlyContainLetters, onlyContainLetters);  
			} 
		}
    }
    
    public void incorrectDateFormat(String element, Object value,Errors errors) {
    	if (!errors.hasFieldErrors(element)) {
			Pattern pattern = Pattern.compile(Constants.DATE_PATTERN.val());  
			Matcher matcher = pattern.matcher(value.toString());
			
			if (!matcher.matches()) { 
				String onlyContainLetters = transactionPage.get("incorrectDateFormat");
				errors.rejectValue(element, onlyContainLetters, onlyContainLetters);  
			} 
		}
    }
    
    public void emailIncorrect(String element, Object value,Errors errors) {
    	if (!errors.hasFieldErrors(element)) {
			Pattern pattern = Pattern.compile(Constants.EMAIL_PATTERN.val());  
			Matcher matcher = pattern.matcher(value.toString());
			
			if (!matcher.matches()) { 
				String onlyContainLetters = transactionPage.get("emailIncorrect");
				errors.rejectValue(element, onlyContainLetters, onlyContainLetters);  
			} 
		}
    }

	public TransactionPage getTransactionPage() {
		return transactionPage;
	}

	public void setTransactionPage(TransactionPage transactionPage) {
		this.transactionPage = transactionPage;
	}
}
