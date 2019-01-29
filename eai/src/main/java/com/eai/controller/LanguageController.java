package com.eai.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eai.dto.Constants;
import com.eai.dto.MessageResponse;
import com.eai.dto.TransactionPage;
import com.eai.model.LogError;
import com.eai.model.SystemParameter;
import com.eai.service.LogErrorService;
import com.eai.service.SystemParameterService;

@Controller
@Scope("prototype")
public class LanguageController {
	
	MessageResponse message;
	
	@Autowired
	LogErrorService logErrorService;
	
	@Autowired
	SystemParameterService systemParametersService;
	
	public static final String LANGUAGE_EN = "/langEN";
	public static final String LANGUAGE_ES = "/langES";
	
	@RequestMapping(path = LANGUAGE_EN, method = RequestMethod.GET)
    public ResponseEntity<MessageResponse> langEN(Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			
			this.lang(request, "EN");
		} catch (Exception exception) {
			message = logErrorService.save(new LogError(exception, LANGUAGE_EN, LANGUAGE_EN));
	    }
		
		return ResponseEntity.ok(message);
	}
	
	@RequestMapping(path = LANGUAGE_ES, method = RequestMethod.GET)
    public ResponseEntity<MessageResponse> langES(Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			
			this.lang(request, "ES");
		} catch (Exception exception) {
			message = logErrorService.save(new LogError(exception, LANGUAGE_ES, LANGUAGE_ES));
	    }
		
		return ResponseEntity.ok(message);
	}
	
	private void lang(HttpServletRequest request, String lang) {
		SystemParameter systemParameter = systemParametersService.findById(7);
		systemParameter.setValue(lang);
		
		systemParametersService.saveOrUpdate(systemParameter);
		
		TransactionPage transactionPage = (TransactionPage) request.getSession().getAttribute(Constants.TRANSACTIONPAGE.val());
		
		if(transactionPage != null) {
			transactionPage.setLocal(lang);
			request.getSession().removeAttribute(Constants.TRANSACTIONPAGE.val());
			request.getSession().setAttribute(Constants.TRANSACTIONPAGE.val(), transactionPage);
		}
	}
}