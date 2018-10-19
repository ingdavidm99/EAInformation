package com.eai.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eai.dto.Constants;
import com.eai.dto.MessageResponse;
import com.eai.dto.Pagination;
import com.eai.dto.TransactionPage;
import com.eai.model.LogError;
import com.eai.model.SystemParameters;
import com.eai.service.LogErrorService;
import com.eai.service.SystemParametersService;
import com.eai.validator.SystemParametersValidator;
import com.eai.validator.SystemParametersValidator1;

@Controller
@Scope("prototype")
public class SystemParametersController {
	
	MessageResponse message = new MessageResponse();
	
	TransactionPage transactionPage;
		
	@Autowired
	LogErrorService logErrorService;
	
	@Autowired
	SystemParametersService systemParametersService;
	
	public static final String PATTH_SYSTEMPARAMETERS = "/systemparameters";
	public static final String PATTH_SEARCH = "/searchsystemparameters";
	public static final String FINDBYSEARCHSYSTEMPARAMETERS = "/findbyidsystemparameters";
	public static final String SAVESYSTEMPARAMETERS = "/savesystemparameters";
	
	@InitBinder("Pagination")
	protected void setupBinder(WebDataBinder binder, HttpServletRequest request) {
		binder.addValidators(new SystemParametersValidator1(TransactionPage.getData(request)));
	}
		
	@RequestMapping(path = PATTH_SYSTEMPARAMETERS, method = RequestMethod.GET)
    public String page(Model model, HttpServletRequest request, @ModelAttribute("Pagination") Pagination pagination) {
		try {
			transactionPage = TransactionPage.getData(request, PATTH_SYSTEMPARAMETERS);
		} catch (Exception exception) {
			message = logErrorService.save(new LogError(exception, transactionPage.getUserName(), PATTH_SYSTEMPARAMETERS));
	    }
		
		model.addAttribute(Constants.TRANSACTIONPAGE.val() ,transactionPage);
		model.addAttribute(Constants.PAGINATION.val(), pagination);	 
		model.addAttribute(Constants.MESSAGESRESPONSE.val(), message);
		
    	return  PATTH_SYSTEMPARAMETERS;
	}
	
	@RequestMapping(path = PATTH_SEARCH, method = RequestMethod.POST)
    public String search(
    		Model model,
    		HttpServletRequest request,
    		@ModelAttribute("Pagination") @Valid Pagination pagination,
    		BindingResult bindingResult) {
		
        try {
        	transactionPage = TransactionPage.getData(request, PATTH_SYSTEMPARAMETERS);
        	
        	if (bindingResult.hasErrors()) {
				for(FieldError error : bindingResult.getFieldErrors()){
					model.addAttribute(error.getField().replace("logError.", ""), error.getDefaultMessage());
				}
				
				message.setStatus(Constants.FAILURE.val());
			 }else {
				 systemParametersService.findAll(pagination, transactionPage.getPageSize());
			 }
        } catch (Exception exception) {
        	message = logErrorService.save(new LogError(exception, transactionPage.getUserName(), PATTH_SEARCH));
        }
        
        model.addAttribute(Constants.TRANSACTIONPAGE.val() ,transactionPage);
        model.addAttribute(Constants.PAGINATION.val(), pagination);
        model.addAttribute(Constants.MESSAGESRESPONSE.val(), message);
        
        return  PATTH_SYSTEMPARAMETERS;
	}
	
	@RequestMapping(path = FINDBYSEARCHSYSTEMPARAMETERS, method = RequestMethod.POST)
    public ResponseEntity<Object> findByIdsystemParameters(
    		Model model, 
    		HttpServletRequest request,
    		@RequestBody SystemParameters idSystemParameters) {
		
        SystemParameters systemParameters = null;
       
        try {
        	transactionPage = TransactionPage.getData(request, PATTH_SYSTEMPARAMETERS);
        	systemParameters = systemParametersService.findById(idSystemParameters.getIdSystemParameters());
        } catch (Exception exception) {
        	message = logErrorService.save(new LogError(exception, transactionPage.getUserName(), FINDBYSEARCHSYSTEMPARAMETERS));
        }
        
    	List<Object> response = new ArrayList<>();
    	response.add(message);
    	response.add(systemParameters);	
    	
        return ResponseEntity.ok(response);
	}
	
	@RequestMapping(path = SAVESYSTEMPARAMETERS, method = RequestMethod.POST)
    public ResponseEntity<Object> save(
    		Model model,
    		HttpServletRequest request,
    		@RequestBody SystemParameters systemParameters,
    		Errors errors) { 
		
		try {
			transactionPage = TransactionPage.getData(request, PATTH_SYSTEMPARAMETERS);
			ValidationUtils.invokeValidator(new SystemParametersValidator(TransactionPage.getData(request)), systemParameters, errors);
			
			if(errors.hasErrors()) {
				message.setErrors(errors.getAllErrors());
				message.setStatus(Constants.FAILURE.val());
			 }else {
				 systemParametersService.saveOrUpdate(systemParameters);
				 message.setStatus(Constants.SUCCESS.val());
			 }
		} catch (Exception exception) {
			message = logErrorService.save(new LogError(exception, transactionPage.getUserName(), SAVESYSTEMPARAMETERS));
		}
		
		return ResponseEntity.ok(message);
	}
}