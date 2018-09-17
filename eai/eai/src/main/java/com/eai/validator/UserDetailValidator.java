package com.eai.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.eai.dto.Constants;
import com.eai.dto.TransactionPage;
import com.eai.model.User;
import com.eai.model.UserDetail;
import com.eai.service.SystemParametersService;
import com.eai.service.UserService;

public class UserDetailValidator implements Validator{
	
	@Autowired
	UserService userService;
	
	@Autowired
	SystemParametersService systemParametersService;
	
	private enum Name {
		FULLNAME("fullName"),
		USERNAME("userName"),
		BIRTH("birth"),
		EMAIL("email"),
		PASSWORD("password"),
		REPEATPASSWORD("repeatPassword");
		
		private String val;
		Name(String val) { this.val = val;}
	    public String val() { return val;}
	}
	
	public UserDetailValidator(UserService userService, SystemParametersService systemParametersService) {
		this.userService = userService;
		this.systemParametersService = systemParametersService;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return UserDetail.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserDetail userDetail = (UserDetail) target;
		TransactionPage transactionPage = new TransactionPage(this.systemParametersService);
		
		String requiredField = transactionPage.get("requiredField");
		String onlyContainLetters = transactionPage.get("onlyContainLetters");
		String yourEmailIncorrect = transactionPage.get("2_emailIncorrect");
		String passwordsMatch = transactionPage.get("2_passwordsNotMatch");
		String privacyPolicy = transactionPage.get("2_privacyPolicy");
		String userAlreadyExists = transactionPage.get("2_userAlreadyExists");
		String incorrectDateFormat = transactionPage.get("2_incorrectDateFormat");
			
		//fullName
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, Name.FULLNAME.val(), requiredField, requiredField);
		if (!errors.hasFieldErrors(Name.FULLNAME.val())) {
			Pattern pattern = Pattern.compile(Constants.NAME_PATTERN.val());  
			Matcher matcher = pattern.matcher(userDetail.getFullName());
			
			if (!matcher.matches()) {  
				errors.rejectValue(Name.FULLNAME.val(), onlyContainLetters, onlyContainLetters);  
			} 
		}
		
		//userName
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, Name.USERNAME.val(), requiredField, requiredField);
		if (!errors.hasFieldErrors(Name.USERNAME.val())) {
			User user = userService.findByUserName(userDetail.getUserName());
			if(user != null)
				errors.rejectValue(Name.USERNAME.val(), userAlreadyExists, userAlreadyExists); 
		}
			
		//birth
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, Name.BIRTH.val(), requiredField, requiredField);
		if (!errors.hasFieldErrors(Name.BIRTH.val())) {
			Pattern pattern = Pattern.compile(Constants.DATE_PATTERN.val());  
			Matcher matcher = pattern.matcher(userDetail.getBirth());
			
			if (!matcher.matches()) {  
				errors.rejectValue(Name.BIRTH.val(), incorrectDateFormat, incorrectDateFormat);  
			} 
		}
		
		//email
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, Name.EMAIL.val(), requiredField, requiredField);
		if (!errors.hasFieldErrors(Name.EMAIL.val())) {  
			Pattern pattern = Pattern.compile(Constants.EMAIL_PATTERN.val());  
			Matcher matcher = pattern.matcher(userDetail.getEmail());  
			   
			if (!matcher.matches()) {  
				errors.rejectValue(Name.EMAIL.val(), yourEmailIncorrect, yourEmailIncorrect);  
			}  
		}  
		
		//password
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, Name.PASSWORD.val(), requiredField, requiredField);
		
		//repeatPassword
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, Name.REPEATPASSWORD.val(), requiredField, requiredField);
		
		//password and repeatPassword
		if (!errors.hasFieldErrors(Name.PASSWORD.val()) 
				&& !errors.hasFieldErrors(Name.REPEATPASSWORD.val())
				&& !userDetail.getPassword().equals(userDetail.getRepeatPassword())) {
			
			errors.rejectValue(Name.PASSWORD.val(), passwordsMatch, passwordsMatch);
		}
		
		//privacyPolicy
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "privacyPolicy", privacyPolicy, privacyPolicy);
	}
}
