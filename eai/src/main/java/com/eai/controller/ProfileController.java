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
		
	public static final String PROFILE = "/profile";
	public static final String UPDATE_PROFILE = "/updateProfile";
	
	@InitBinder("UserDetail")
	protected void setupBinder(WebDataBinder binder, HttpServletRequest request) {
		binder.addValidators(new ProfileValidator(userService, TransactionPage.getData(request)));
	}
	
	@RequestMapping(path = PROFILE, method = RequestMethod.GET)
    public String page(Model model, HttpServletRequest request) {
		
		UserDetail userDetail = new UserDetail();
		try {
			transactionPage = TransactionPage.getData(request, PROFILE);
			
			User user = userService.findByUserName(transactionPage.getUserName());

			userDetail = user.getUserDetail();
			userDetail.setUserName(user.getUsername());
			userDetail.setRoleName(user.getRole().getName());
		} catch (Exception exception) {
			message = logErrorService.save(new LogError(exception, transactionPage.getUserName(), PROFILE));
	    }
		
		model.addAttribute(Constants.TRANSACTIONPAGE.val(), transactionPage);
		model.addAttribute("UserDetail", userDetail);
		model.addAttribute(Constants.MESSAGESRESPONSE.val(), message);
    	
		return  PROFILE;
	}
	
	@RequestMapping(path = UPDATE_PROFILE, method = RequestMethod.POST)
    public String updateProfile(
    		Model model,
    		HttpServletRequest request,
    		@ModelAttribute("UserDetail") @Valid UserDetail userDetail,
    		BindingResult bindingResult) {
		
		try {
			transactionPage = TransactionPage.getData(request, PROFILE);
			
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
				  
				 User user = userService.findByUserName(transactionPage.getUserName());
				 userDetail.setUserName(user.getUsername());
				 userDetail.setRoleName(user.getRole().getName());
				 
				 message.setStatus(Constants.SUCCESS.val());
			 }
						
			
		} catch (Exception exception) {
			message = logErrorService.save(new LogError(exception, transactionPage.getUserName(), UPDATE_PROFILE));
		}
		
		model.addAttribute(Constants.TRANSACTIONPAGE.val(), transactionPage);
		model.addAttribute("UserDetail", userDetail);
		model.addAttribute(Constants.MESSAGESRESPONSE.val(), message);
		
		return  PROFILE;
	}
}