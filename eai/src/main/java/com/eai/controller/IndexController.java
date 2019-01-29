package com.eai.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eai.dto.Constants;
import com.eai.dto.MessageResponse;
import com.eai.dto.Pagination;
import com.eai.dto.TransactionPage;
import com.eai.model.LogError;
import com.eai.service.LogErrorService;
import com.eai.service.MenuService;
import com.eai.service.ParentMenuService;
import com.eai.service.SystemParameterService;
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
	SystemParameterService systemParametersService;
	
	public static final String INDEX = "/index";
	
	@RequestMapping(path = INDEX, method = RequestMethod.GET)
    public String page(Model model, HttpServletRequest request, @ModelAttribute("Pagination") Pagination pagination) {
		try {
			transactionPage = (TransactionPage) request.getSession().getAttribute(Constants.TRANSACTIONPAGE.val());
			
			if(transactionPage != null) {
				transactionPage = TransactionPage.getData(request, INDEX);
			}else {
				transactionPage = TransactionPage.getData(request, userService, parentMenuService, menuService, systemParametersService);
				
				request.getSession().removeAttribute(Constants.TRANSACTIONPAGE.val());
				request.getSession().setAttribute(Constants.TRANSACTIONPAGE.val(), transactionPage);
			}
		} catch (Exception exception) {
			if(transactionPage != null)
				message = logErrorService.save(new LogError(exception, transactionPage.getUserName(), INDEX));
	    }
		
		model.addAttribute(Constants.TRANSACTIONPAGE.val(), transactionPage);
		model.addAttribute(Constants.PAGINATION.val(), pagination);	 
		model.addAttribute(Constants.MESSAGESRESPONSE.val(), message);
		
    	return INDEX;
	}
}