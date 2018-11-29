package com.eai.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eai.dto.Constants;
import com.eai.dto.MessageResponse;
import com.eai.dto.TransactionPage;
import com.eai.model.LogError;
import com.eai.model.Rule;
import com.eai.service.LogErrorService;
import com.eai.service.RuleService;
import com.eai.wizard.service.ExtractInformationService;

@Controller
@Scope("prototype")
public class RuleTestController {
	
	MessageResponse message = new MessageResponse();
	
	TransactionPage transactionPage;
		
	@Autowired
	LogErrorService logErrorService;
		
	@Autowired
	ExtractInformationService extractInformationService;
	
	@Autowired
	RuleService ruleService;
	
	public static final String RULETEST = "/ruletest";
		
	@RequestMapping(path = RULETEST + "/{id}", method = RequestMethod.GET)
    public String page(Model model, HttpServletRequest request, @PathVariable("id") int id) {
		try {
			transactionPage = TransactionPage.getData(request, RULETEST + "/0");
		} catch (Exception exception) {
			message = logErrorService.save(new LogError(exception, transactionPage.getUserName(), RULETEST));
	    }
		
		Rule rule = new Rule();
		if(id > 0) rule = ruleService.findById(id);
				
		model.addAttribute(Constants.TRANSACTIONPAGE.val(), transactionPage);
		model.addAttribute(Constants.MESSAGESRESPONSE.val(), message);
		model.addAttribute("Rule", rule);
		
    	return  RULETEST;
	}
}