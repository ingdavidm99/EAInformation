package com.eai.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eai.dto.Constants;
import com.eai.dto.MessageResponse;
import com.eai.model.LogError;
import com.eai.model.SystemParameter;
import com.eai.model.User;
import com.eai.service.LogErrorService;
import com.eai.service.SystemParameterService;

@Controller
@Scope("prototype")
public class SignInController {
	
	MessageResponse message = new MessageResponse();
	
	@Autowired
	LogErrorService logErrorService;
	
	@Autowired
	SystemParameterService systemParametersService;
	
	public static final String SIGNIN = "/signin";
	public static final String LOGOUT = "/logout";
	
	@RequestMapping(path = SIGNIN, method = RequestMethod.GET)
    public String page(Model model, HttpServletRequest request) {
		try {
	        model.addAttribute("user", new User());
	        
	        Object messageRequest = request.getSession().getAttribute(Constants.MESSAGESRESPONSE.val());
	        message = (MessageResponse) messageRequest;
	        
	        request.getSession().removeAttribute(Constants.MESSAGESRESPONSE.val());
	     } catch (Exception exception) {
	    	 message = logErrorService.save(new LogError(exception, null, SIGNIN));
	    }
		
		model.addAttribute(Constants.MESSAGESRESPONSE.val(), message);
		
        return SIGNIN;
    }
	
	@RequestMapping(path = LOGOUT, method = RequestMethod.GET)
	public String logoutPage (Model model, HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){    
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		
		SystemParameter systemParameter = systemParametersService.findById(7);
		systemParameter.setValue("EN");
		
		systemParametersService.saveOrUpdate(systemParameter);
		
		request.getSession().setAttribute("logoutTimeOut", LOGOUT);
		
		return "redirect:/signin";
	}
	
	@RequestMapping("/access_denied")
    public String accessDenied(Model model, HttpServletRequest request) {
    	return "access_denied";
    }
}