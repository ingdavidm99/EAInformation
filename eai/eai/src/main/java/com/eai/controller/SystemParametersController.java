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

@Controller
@Scope("prototype")
public class SystemParametersController {
	
	MessageResponse message;
	
	TransactionPage transactionPage = new TransactionPage();
	
	@Autowired
	LogErrorService logErrorService;
	
	public static final String PATTH_SYSTEMPARAMETERS = "/systemparameters";
		
	@RequestMapping(path = PATTH_SYSTEMPARAMETERS, method = RequestMethod.GET)
    public String page(Model model, HttpServletRequest request, @ModelAttribute("Pagination") Pagination pagination) {
		try {
			transactionPage = TransactionPage.getTransactionPage(request, PATTH_SYSTEMPARAMETERS);
		} catch (Exception exception) {
			message = logErrorService.save(new LogError(exception, transactionPage.getUserName(), PATTH_SYSTEMPARAMETERS));
	    }
		
		model.addAttribute(Constants.TRANSACTIONPAGE.val() ,transactionPage);
		model.addAttribute(Constants.PAGINATION.val(), pagination);	 
		model.addAttribute(Constants.MESSAGESRESPONSE.val(), message);
		
    	return  PATTH_SYSTEMPARAMETERS;
	}
}