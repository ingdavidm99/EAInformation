package com.eai.controller;

import javax.servlet.http.HttpServletRequest;
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
import com.eai.dto.TransactionPage;
import com.eai.model.LogError;
import com.eai.model.User;
import com.eai.model.UserDetail;
import com.eai.service.LogErrorService;
import com.eai.service.SystemParametersService;
import com.eai.service.UserDetailService;
import com.eai.service.UserService;
import com.eai.validator.ProfileValidator;

@Controller
@Scope("prototype")
public class ProfileController {
	
	MessageResponse message = new MessageResponse();
	
	TransactionPage transactionPage;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserDetailService userDetailService;
		
	@Autowired
	LogErrorService logErrorService;
	
	@Autowired
	SystemParametersService systemParametersService;
	
	public static final String PATTH_PROFILE = "/profile";
	public static final String PATTH_UPDATE_PROFILE = "/updateProfile";
	
	@InitBinder("UserDetail")
	protected void setupBinder(WebDataBinder binder) {
	    binder.addValidators(new ProfileValidator(userService, systemParametersService));
	}
	
	@RequestMapping(path = PATTH_PROFILE, method = RequestMethod.GET)
    public String page(Model model, HttpServletRequest request) {
		
		UserDetail userDetail = new UserDetail();
		try {
			transactionPage = TransactionPage.getTransactionPage(request, PATTH_PROFILE);
			
			User user = userService.findByUserName(transactionPage.getUserName());

			userDetail = user.getUserDetail();
			userDetail.setUserName(user.getUsername());
			userDetail.setRoleName(user.getRole().getName());
		} catch (Exception exception) {
			message = logErrorService.save(new LogError(exception, transactionPage.getUserName(), PATTH_PROFILE));
	    }
		
		model.addAttribute("tp", transactionPage);
		model.addAttribute("UserDetail", userDetail);
		model.addAttribute(Constants.MESSAGESRESPONSE.val(), message);
    	
		return  PATTH_PROFILE;
	}
	
	@RequestMapping(path = PATTH_UPDATE_PROFILE, method = RequestMethod.POST)
    public String registration(
    		Model model,
    		HttpServletRequest request,
    		@ModelAttribute("UserDetail") @Valid UserDetail userDetail,
    		BindingResult bindingResult) {
		
		try {
			transactionPage = TransactionPage.getTransactionPage(request, PATTH_PROFILE);
			
			if (bindingResult.hasErrors()) {
				for(FieldError error : bindingResult.getFieldErrors()){
					model.addAttribute(error.getField(), error.getDefaultMessage());
				}
				
				message.setStatus(Constants.FAILURE.val());
			 }else {
				  if("1".equals(userDetail.getOption())) {
					 userDetailService.updateUserDetail(userDetail);
				 }else {
					 userDetailService.updateUserPassword(userDetail);
				 }
				 
				 message.setStatus(Constants.SUCCESS.val());
			 }
						
			
		} catch (Exception exception) {
			message = logErrorService.save(new LogError(exception, transactionPage.getUserName(), PATTH_UPDATE_PROFILE));
		}
		
		model.addAttribute("tp", transactionPage);
		model.addAttribute("UserDetail", userDetail);
		model.addAttribute(Constants.MESSAGESRESPONSE.val(), message);
		
		return  PATTH_PROFILE;
	}
}