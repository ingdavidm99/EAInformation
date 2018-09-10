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

import com.eai.dto.MessageResponse;
import com.eai.model.LogError;
import com.eai.model.SystemParameters;
import com.eai.service.LogErrorService;
import com.eai.service.SystemParametersService;

@Controller
@Scope("prototype")
public class LanguageController {
	
	MessageResponse message;
	
	@Autowired
	LogErrorService logErrorService;
	
	@Autowired
	SystemParametersService systemParametersService;
	
	public static final String PATTH_LANGUAGE_EN = "/langEN";
	public static final String PATTH_LANGUAGE_ES = "/langES";
	
	@RequestMapping(path = PATTH_LANGUAGE_EN, method = RequestMethod.GET)
    public ResponseEntity<MessageResponse> langEN(Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			
			SystemParameters systemParameters = systemParametersService.findById(7);
			systemParameters.setValue("EN");
			
			systemParametersService.saveOrUpdate(systemParameters);
		} catch (Exception exception) {
			message = logErrorService.save(new LogError(exception, PATTH_LANGUAGE_EN, PATTH_LANGUAGE_EN));
	    }
		
		return ResponseEntity.ok(message);
	}
	
	@RequestMapping(path = PATTH_LANGUAGE_ES, method = RequestMethod.GET)
    public ResponseEntity<MessageResponse> langES(Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			
			SystemParameters systemParameters = systemParametersService.findById(7);
			systemParameters.setValue("ES");
			
			systemParametersService.saveOrUpdate(systemParameters);
		} catch (Exception exception) {
			message = logErrorService.save(new LogError(exception, PATTH_LANGUAGE_ES, PATTH_LANGUAGE_ES));
	    }
		
		return ResponseEntity.ok(message);
	}
}