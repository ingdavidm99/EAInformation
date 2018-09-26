package com.eai.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eai.dto.Constants;
import com.eai.dto.MessageResponse;
import com.eai.dto.TransactionPage;
import com.eai.model.LogError;
import com.eai.service.LogErrorService;
import com.eai.service.MenuService;
import com.eai.service.ParentMenuService;
import com.eai.service.SystemParametersService;
import com.eai.service.UserService;

@Controller
@Scope("prototype")
public class IndexController {
	
	MessageResponse message;
	
	TransactionPage transactionPage;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ParentMenuService parentMenuService;
	
	@Autowired
	MenuService menuService;
	
	@Autowired
	LogErrorService logErrorService;
	
	@Autowired
	SystemParametersService systemParametersService;
	
	public static final String PATTH_INDEX = "/index";
	
	@RequestMapping(path = PATTH_INDEX, method = RequestMethod.GET)
    public String page(Model model, HttpServletRequest request) {
		try {
			transactionPage = (TransactionPage) request.getSession().getAttribute(Constants.TRANSACTIONPAGE.val());
			
			if(transactionPage != null) {
				transactionPage = TransactionPage.getTransactionPage(request, PATTH_INDEX);
			}else {
				transactionPage = TransactionPage.getData(request, userService, parentMenuService, menuService, systemParametersService);
				
				request.getSession().removeAttribute(Constants.TRANSACTIONPAGE.val());
				request.getSession().setAttribute(Constants.TRANSACTIONPAGE.val(), transactionPage);
			}
		} catch (Exception exception) {
			message = logErrorService.save(new LogError(exception, transactionPage.getUserName(), PATTH_INDEX));
	    }
		
		model.addAttribute(Constants.TRANSACTIONPAGE.val(), transactionPage);
		model.addAttribute(Constants.MESSAGESRESPONSE.val(), message);
		
    	return PATTH_INDEX;
	}
}