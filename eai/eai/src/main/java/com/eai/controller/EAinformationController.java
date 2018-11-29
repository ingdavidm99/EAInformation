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
import com.eai.model.Rule;
import com.eai.service.LogErrorService;
import com.eai.service.SystemParameterService;
import com.eai.wizard.service.ExtractInformationService;

@Controller
@Scope("prototype")
public class EAinformationController {
	
	MessageResponse message = new MessageResponse();
	
	TransactionPage transactionPage;
		
	@Autowired
	LogErrorService logErrorService;
	
	@Autowired
	SystemParameterService systemParametersService;
	
	@Autowired
	ExtractInformationService extractInformationService;
	
	public static final String EAINFORMATION = "/eainformation";
		
	@RequestMapping(path = EAINFORMATION, method = RequestMethod.GET)
    public String page(Model model, HttpServletRequest request) {
		try {
			transactionPage = TransactionPage.getData(request, EAINFORMATION);
		} catch (Exception exception) {
			message = logErrorService.save(new LogError(exception, transactionPage.getUserName(), EAINFORMATION));
	    }
		
		Rule rule = new Rule();
		
		rule.setBaseUrl("https://www.gamestorrents.tv");
		
		rule.setUrlRegex("[(href)#bs-example-navbar-collapse-1 .nav li a:not(.active)]");
		rule.setPaginationUrl(false);
		rule.setPaginationUrlRegex("");
		
		rule.setLinkRegex("[(href)#home h6 a]");
		rule.setPaginationLink(true);
		rule.setPaginationLinkRegex("[(!?)page/{0}/]");
		
		rule.setDescriptionRegex(
				"[(title).listencio.*?\\n.*?li.*?<strong>(.*?)<\\/strong><\\/li>]"
			  + "\n"
			  + "[(img)img.*?post_imagem.*?src=\"(.*?)\"]");
				
		model.addAttribute(Constants.TRANSACTIONPAGE.val(), transactionPage);
		model.addAttribute(Constants.MESSAGESRESPONSE.val(), message);
		model.addAttribute("Rule", rule);
		
    	return  EAINFORMATION;
	}
}