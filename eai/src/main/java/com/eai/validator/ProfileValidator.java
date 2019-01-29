package com.eai.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.eai.model.User;
import com.eai.model.UserDetail;
import com.eai.service.UserService;

public class ProfileValidator extends ParentValidator implements Validator{
	
	@Autowired
	UserService userService;
	
	public ProfileValidator(UserService userService, String local) {
		super(local);
		this.userService = userService;
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
		
		String requiredField = getTransactionPage().get("requiredField");
		
		if("1".equals(userDetail.getOption())) {
			//fullName
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, Name.FULLNAME.val(), requiredField, requiredField);
			onlyContainLetters(Name.FULLNAME.val(), userDetail.getFullName(), errors);
			
			//birth
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, Name.BIRTH.val(), requiredField, requiredField);
			incorrectDateFormat(Name.BIRTH.val(), userDetail.getBirth(), errors);
			
			//email
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, Name.EMAIL.val(), requiredField, requiredField);
			emailIncorrect(Name.EMAIL.val(), userDetail.getEmail(), errors);
			
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
			
			String passwordsMatch = getTransactionPage().get("2_passwordsNotMatch");
			
			errors.rejectValue(Name.NEWPASSWORD.val(), passwordsMatch, passwordsMatch);
		} 
	}
}
