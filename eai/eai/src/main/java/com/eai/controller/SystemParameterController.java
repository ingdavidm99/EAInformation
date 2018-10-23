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
import com.eai.model.SystemParameter;
import com.eai.service.LogErrorService;
import com.eai.service.SystemParametersService;
import com.eai.validator.SystemParametersValidator;
import com.eai.validator.SystemParametersValidator1;

@Controller
@Scope("prototype")
public class SystemParameterController {
	
	MessageResponse message = new MessageResponse();
	
	TransactionPage transactionPage;
		
	@Autowired
	LogErrorService logErrorService;
	
	@Autowired
	SystemParametersService systemParametersService;
	
	public static final String PATTH_SYSTEMPARAMETER = "/systemparameter";
	public static final String PATTH_SEARCH_SYSTEMPARAMETER = "/searchSystemparameter";
	public static final String PATTH_FINDBY_ID_SYSTEMPARAMETER = "/findByIdSystemparameter";
	public static final String PATTH_UPDATE_SYSTEMPARAMETER = "/updateSystemparameter";
	
	@InitBinder("Pagination")
	protected void setupBinder(WebDataBinder binder, HttpServletRequest request) {
		binder.addValidators(new SystemParametersValidator1(TransactionPage.getData(request)));
	}
		
	@RequestMapping(path = PATTH_SYSTEMPARAMETER, method = RequestMethod.GET)
    public String page(Model model, HttpServletRequest request) {
		try {
			transactionPage = TransactionPage.getData(request, PATTH_SYSTEMPARAMETER);
		} catch (Exception exception) {
			message = logErrorService.save(new LogError(exception, transactionPage.getUserName(), PATTH_SYSTEMPARAMETER));
	    }
		
		model.addAttribute(Constants.TRANSACTIONPAGE.val() ,transactionPage);
		model.addAttribute(Constants.PAGINATION.val(), new Pagination());	 
		model.addAttribute(Constants.MESSAGESRESPONSE.val(), message);
		
    	return  PATTH_SYSTEMPARAMETER;
	}
	
	@RequestMapping(path = PATTH_SEARCH_SYSTEMPARAMETER, method = RequestMethod.POST)
    public String searchSystemparameter(
    		Model model,
    		HttpServletRequest request,
    		@ModelAttribute("Pagination") @Valid Pagination pagination,
    		BindingResult bindingResult) {
		
        try {
        	transactionPage = TransactionPage.getData(request, PATTH_SYSTEMPARAMETER);
        	
        	if (bindingResult.hasErrors()) {
				message.setStatus(Constants.FAILURE.val());
			 }else {
				 systemParametersService.findAll(pagination, transactionPage.getPageSize());
			 }
        } catch (Exception exception) {
        	message = logErrorService.save(new LogError(exception, transactionPage.getUserName(), PATTH_SEARCH_SYSTEMPARAMETER));
        }
        
        model.addAttribute(Constants.TRANSACTIONPAGE.val() ,transactionPage);
        model.addAttribute(Constants.PAGINATION.val(), pagination);
        model.addAttribute(Constants.MESSAGESRESPONSE.val(), message);
        
        return  PATTH_SYSTEMPARAMETER;
	}
	
	@RequestMapping(path = PATTH_FINDBY_ID_SYSTEMPARAMETER, method = RequestMethod.POST)
    public ResponseEntity<Object> findByIdsystemParameters(
    		Model model, 
    		HttpServletRequest request,
    		@RequestBody SystemParameter idSystemParameters) {
		
        SystemParameter systemParameter = null;
       
        try {
        	transactionPage = TransactionPage.getData(request, PATTH_SYSTEMPARAMETER);
        	systemParameter = systemParametersService.findById(idSystemParameters.getIdSystemParameter());
        } catch (Exception exception) {
        	message = logErrorService.save(new LogError(exception, transactionPage.getUserName(), PATTH_FINDBY_ID_SYSTEMPARAMETER));
        }
        
    	List<Object> response = new ArrayList<>();
    	response.add(message);
    	response.add(systemParameter);	
    	
        return ResponseEntity.ok(response);
	}
	
	@RequestMapping(path = PATTH_UPDATE_SYSTEMPARAMETER, method = RequestMethod.POST)
    public ResponseEntity<Object> updateSystemparameter(
    		Model model,
    		HttpServletRequest request,
    		@RequestBody SystemParameter systemParameter,
    		Errors errors) { 
		
		try {
			transactionPage = TransactionPage.getData(request, PATTH_SYSTEMPARAMETER);
			ValidationUtils.invokeValidator(new SystemParametersValidator(TransactionPage.getData(request)), systemParameter, errors);
			
			if(errors.hasErrors()) {
				message.setErrors(errors.getAllErrors());
				message.setStatus(Constants.FAILURE.val());
			 }else {
				 systemParametersService.saveOrUpdate(systemParameter);
				 message.setStatus(Constants.SUCCESS.val());
			 }
		} catch (Exception exception) {
			message = logErrorService.save(new LogError(exception, transactionPage.getUserName(), PATTH_UPDATE_SYSTEMPARAMETER));
		}
		
		return ResponseEntity.ok(message);
	}
}