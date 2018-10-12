package com.eai.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eai.dto.Constants;
import com.eai.dto.MessageResponse;
import com.eai.model.LogError;
import com.eai.model.UserDetail;
import com.eai.service.LogErrorService;
import com.eai.service.SystemParametersService;
import com.eai.service.UserDetailService;
import com.eai.service.UserService;
import com.eai.validator.UserDetailValidator;

@Controller
@Scope("prototype")
public class SignUpController {
	
	MessageResponse message = new MessageResponse();
	
	@Autowired
	LogErrorService logErrorService;
	
	@Autowired
	UserDetailService userDetailService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	private SystemParametersService systemParametersService;
	
	public static final String PATTH_SIGNUP = "/signup";
	public static final String PATTH_REGISTRATION = "/registration";
	
	@InitBinder("UserDetail")
	protected void setupBinder(WebDataBinder binder) {
		String local = systemParametersService.findById(7).getValue();
	    binder.addValidators(new UserDetailValidator(userService, local));
	}
		
	@RequestMapping(path = PATTH_SIGNUP, method = RequestMethod.GET)
    public String page(Model model) {
		try {
			model.addAttribute("UserDetail", new UserDetail());
		} catch (Exception exception) {
			message = logErrorService.save(new LogError(exception, "N/A", PATTH_SIGNUP));
	    }
		
		model.addAttribute(Constants.MESSAGESRESPONSE.val(), message);
		
    	return  PATTH_SIGNUP;
	}
	
	@RequestMapping(path = PATTH_REGISTRATION, method = RequestMethod.POST)
    public String registration(
    		Model model, 
    		@ModelAttribute("UserDetail") @Valid UserDetail userDetail,
    		BindingResult bindingResult) {
		
		try {
			if (bindingResult.hasErrors()) {
				for(FieldError error : bindingResult.getFieldErrors()){
					model.addAttribute(error.getField(), error.getDefaultMessage());
				}
				
				message.setStatus(Constants.FAILURE.val());
			 }else {
				 message.setStatus(Constants.SUCCESS.val());
				 userDetailService.saveUserDetail(userDetail);
			 }
		} catch (Exception exception) {
			message = logErrorService.save(new LogError(exception, "N/A", PATTH_REGISTRATION));
		}
		
		model.addAttribute("UserDetail", userDetail);
		model.addAttribute(Constants.MESSAGESRESPONSE.val(), message);
		
		return  PATTH_SIGNUP;
	}
}