package com.eai.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.eai.model.User;
import com.eai.model.UserDetail;
import com.eai.service.UserService;

public class UserDetailValidator extends ParentValidator implements Validator{
	
	@Autowired
	UserService userService;
	
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
	
	public UserDetailValidator(UserService userService, String local) {
		super(local);
		this.userService = userService;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return UserDetail.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserDetail userDetail = (UserDetail) target;
		
		String requiredField = getTransactionPage().get("requiredField");
		String passwordsMatch = getTransactionPage().get("2_passwordsNotMatch");
		String privacyPolicy = getTransactionPage().get("2_privacyPolicy");
		String userAlreadyExists = getTransactionPage().get("2_userAlreadyExists");
			
		//fullName
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, Name.FULLNAME.val(), requiredField, requiredField);
		onlyContainLetters(Name.FULLNAME.val(), userDetail.getFullName(), errors);
		
		//userName
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, Name.USERNAME.val(), requiredField, requiredField);
		if (!errors.hasFieldErrors(Name.USERNAME.val())) {
			User user = userService.findByUserName(userDetail.getUserName());
			if(user != null)
				errors.rejectValue(Name.USERNAME.val(), userAlreadyExists, userAlreadyExists); 
		}
			
		//birth
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, Name.BIRTH.val(), requiredField, requiredField);
		incorrectDateFormat(Name.BIRTH.val(), userDetail.getBirth(), errors);
		
		//email
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, Name.EMAIL.val(), requiredField, requiredField);
		emailIncorrect(Name.EMAIL.val(), userDetail.getEmail(), errors);  
		
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
