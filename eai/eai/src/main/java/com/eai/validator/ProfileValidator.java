package com.eai.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.eai.dto.Constants;
import com.eai.dto.TransactionPage;
import com.eai.model.User;
import com.eai.model.UserDetail;
import com.eai.service.SystemParametersService;
import com.eai.service.UserService;

public class ProfileValidator implements Validator{
	
	TransactionPage transactionPage;
	
	@Autowired
	UserService userService;
	
	@Autowired
	SystemParametersService systemParametersService;
	
	public ProfileValidator(UserService userService, SystemParametersService systemParametersService) {
		this.userService = userService;
		this.systemParametersService = systemParametersService;
	}
	
	private enum Name {
		FULLNAME("fullName"),
		BIRTH("birth"),
		EMAIL("email"),
		PASSWORD("password"),
		REPEATPASSWORD("repeatPassword"),
		NEWPASSWORD("newPassword");
		
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
		return UserDetail.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserDetail userDetail = (UserDetail) target;
		transactionPage = new TransactionPage(this.systemParametersService);
		
		String requiredField = transactionPage.get("requiredField");
		
		if("1".equals(userDetail.getOption())) {
			//fullName
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, Name.FULLNAME.val(), requiredField, requiredField);
			onlyContainLetters(userDetail, errors);
			
			//birth
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, Name.BIRTH.val(), requiredField, requiredField);
			incorrectDateFormat(userDetail, errors);
			
			//email
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, Name.EMAIL.val(), requiredField, requiredField);
			emailIncorrect(userDetail, errors);
			
		}else {
			User user = userService.findByUserName(userDetail.getUserName());
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
			
			//password
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, Name.PASSWORD.val(), requiredField, requiredField);
			
			//password not exit
			passwordNotExit(userDetail, user, passwordEncoder, errors);
			
			//newPassword
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, Name.NEWPASSWORD.val(), requiredField, requiredField);
			
			//password == newPassword
			boolean vaida = passwordEqualsNewPassword(userDetail, user, passwordEncoder, errors);
			
			//repeatPassword
			if(vaida) {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, Name.REPEATPASSWORD.val(), requiredField, requiredField);
			}			
			
			//newPassword != repeatPassword
			newPasswordNotEqualsrepeatPassword(userDetail, errors);
		}
	}
	
	private void onlyContainLetters(UserDetail userDetail, Errors errors) {
		if (!errors.hasFieldErrors(Name.FULLNAME.val())) {
			String onlyContainLetters = transactionPage.get("onlyContainLetters");
			Pattern pattern = Pattern.compile(Constants.NAME_PATTERN.val());  
			Matcher matcher = pattern.matcher(userDetail.getFullName());
			
			if (!matcher.matches()) {  
				errors.rejectValue(Name.FULLNAME.val(), onlyContainLetters, onlyContainLetters);  
			} 
		}
	}
	
	private void incorrectDateFormat(UserDetail userDetail, Errors errors) {
		if (!errors.hasFieldErrors(Name.BIRTH.val())) {
			String incorrectDateFormat = transactionPage.get("2_incorrectDateFormat");
			Pattern pattern = Pattern.compile(Constants.DATE_PATTERN.val());  
			Matcher matcher = pattern.matcher(userDetail.getBirth());
			
			if (!matcher.matches()) {  
				errors.rejectValue(Name.BIRTH.val(), incorrectDateFormat, incorrectDateFormat);  
			} 
		}
	}
	
	private void emailIncorrect(UserDetail userDetail, Errors errors) {
		if (!errors.hasFieldErrors(Name.EMAIL.val())) { 
			String yourEmailIncorrect = transactionPage.get("2_emailIncorrect");
			Pattern pattern = Pattern.compile(Constants.EMAIL_PATTERN.val());  
			Matcher matcher = pattern.matcher(userDetail.getEmail());  
			   
			if (!matcher.matches()) {  
				errors.rejectValue(Name.EMAIL.val(), yourEmailIncorrect, yourEmailIncorrect);  
			}  
		} 
	}
	
	private void passwordNotExit(UserDetail userDetail, User user, BCryptPasswordEncoder passwordEncoder, Errors errors) {
		if (!errors.hasFieldErrors(Name.PASSWORD.val())
				&& !passwordEncoder.matches(userDetail.getPassword(), user.getPassword())){
				errors.rejectValue(Name.PASSWORD.val(), "NOT MATCH", "NOT MATCH");
			}
	}
	
	private boolean passwordEqualsNewPassword(UserDetail userDetail, User user, BCryptPasswordEncoder passwordEncoder, Errors errors) {
		if (!errors.hasFieldErrors(Name.PASSWORD.val()) 
				&& !errors.hasFieldErrors(Name.NEWPASSWORD.val())
				&& passwordEncoder.matches(userDetail.getNewPassword(), user.getPassword())){
		
			errors.rejectValue(Name.NEWPASSWORD.val(), "NEW PASSWORD IQUALS TO PASSWORD", "NEW PASSWORD IQUALS TO PASSWORD");
			return false;
		}
		
		return true;
	}
	
	private void newPasswordNotEqualsrepeatPassword(UserDetail userDetail, Errors errors) {
		if (!errors.hasFieldErrors(Name.PASSWORD.val())
				&& !errors.hasFieldErrors(Name.NEWPASSWORD.val()) 
				&& !errors.hasFieldErrors(Name.REPEATPASSWORD.val())
				&& !userDetail.getNewPassword().equals(userDetail.getRepeatPassword())) {
			
			String passwordsMatch = transactionPage.get("2_passwordsNotMatch");
			
			errors.rejectValue(Name.NEWPASSWORD.val(), passwordsMatch, passwordsMatch);
		} 
	}
}
