package com.eai.controller;

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
import com.eai.model.SystemParameter;
import com.eai.service.LogErrorService;
import com.eai.service.SystemParameterService;
import com.eai.validator.SearchSystemParameterValidator;
import com.eai.validator.SystemParameterValidator;

@Controller
@Scope("prototype")
public class SystemParameterController {
	
	MessageResponse message = new MessageResponse();
	
	TransactionPage transactionPage;
		
	@Autowired
	LogErrorService logErrorService;
	
	@Autowired
	SystemParameterService systemParametersService;
	
	public static final String SYSTEMPARAMETER = "/systemparameter";
	public static final String SEARCH_SYSTEMPARAMETER = "/searchSystemparameter";
	public static final String FINDBY_ID_SYSTEMPARAMETER = "/findByIdSystemparameter";
	public static final String UPDATE_SYSTEMPARAMETER = "/updateSystemparameter";
	
	@InitBinder("Pagination")
	protected void setupBinder(WebDataBinder binder, HttpServletRequest request) {
		binder.addValidators(new SearchSystemParameterValidator(TransactionPage.getData(request)));
	}
		
	@RequestMapping(path = SYSTEMPARAMETER, method = RequestMethod.GET)
    public String page(Model model, HttpServletRequest request) {
		try {
			transactionPage = TransactionPage.getData(request, SYSTEMPARAMETER);
		} catch (Exception exception) {
			message = logErrorService.save(new LogError(exception, transactionPage.getUserName(), SYSTEMPARAMETER));
	    }
		
		model.addAttribute(Constants.TRANSACTIONPAGE.val() ,transactionPage);
		model.addAttribute(Constants.PAGINATION.val(), new Pagination());	 
		model.addAttribute(Constants.MESSAGESRESPONSE.val(), message);
		
    	return  SYSTEMPARAMETER;
	}
	
	@RequestMapping(path = SEARCH_SYSTEMPARAMETER, method = RequestMethod.POST)
    public String searchSystemparameter(
    		Model model,
    		HttpServletRequest request,
    		@ModelAttribute("Pagination") @Valid Pagination pagination,
    		BindingResult bindingResult) {
		
        try {
        	transactionPage = TransactionPage.getData(request, SYSTEMPARAMETER);
        	
        	if (bindingResult.hasErrors()) {
        		for(FieldError error : bindingResult.getFieldErrors()){
        			model.addAttribute(error.getField().replace("[","_").replace("]",""), error.getDefaultMessage());
				}
        		
				message.setStatus(Constants.FAILURE.val());
			 }else {
				 systemParametersService.findAll(pagination, transactionPage.getPageSize());
			 }
        } catch (Exception exception) {
        	message = logErrorService.save(new LogError(exception, transactionPage.getUserName(), SEARCH_SYSTEMPARAMETER));
        }
        
        model.addAttribute(Constants.TRANSACTIONPAGE.val() ,transactionPage);
        model.addAttribute(Constants.PAGINATION.val(), pagination);
        model.addAttribute(Constants.MESSAGESRESPONSE.val(), message);
        
        return  SYSTEMPARAMETER;
	}
	
	@RequestMapping(path = FINDBY_ID_SYSTEMPARAMETER, method = RequestMethod.POST)
    public ResponseEntity<Object> findByIdsystemParameters(
    		Model model, 
    		HttpServletRequest request,
    		@RequestBody SystemParameter idSystemParameters) {
		
        SystemParameter systemParameter = null;
       
        try {
        	transactionPage = TransactionPage.getData(request, SYSTEMPARAMETER);
        	systemParameter = systemParametersService.findById(idSystemParameters.getIdSystemParameter());
        } catch (Exception exception) {
        	message = logErrorService.save(new LogError(exception, transactionPage.getUserName(), FINDBY_ID_SYSTEMPARAMETER));
        }
        
    	if(systemParameter != null) {
    		message.setData("idSystemParameter", systemParameter.getIdSystemParameter());
        	message.setData("name", systemParameter.getName());
        	message.setData("value", systemParameter.getValue());
        	message.setData("description", systemParameter.getDescription());
        	message.setData("type", systemParameter.getType());
    	}
    	
        return ResponseEntity.ok(message);
	}
	
	@RequestMapping(path = UPDATE_SYSTEMPARAMETER, method = RequestMethod.POST)
    public ResponseEntity<Object> updateSystemparameter(
    		Model model,
    		HttpServletRequest request,
    		@RequestBody SystemParameter systemParameter,
    		Errors errors) { 
		
		try {
			transactionPage = TransactionPage.getData(request, SYSTEMPARAMETER);
			ValidationUtils.invokeValidator(new SystemParameterValidator(TransactionPage.getData(request)), systemParameter, errors);
			
			if(errors.hasErrors()) {
				message.setErrors(errors.getAllErrors());
				message.setStatus(Constants.FAILURE.val());
			 }else {
				 systemParametersService.saveOrUpdate(systemParameter);
				 message.setStatus(Constants.SUCCESS.val());
			 }
		} catch (Exception exception) {
			message = logErrorService.save(new LogError(exception, transactionPage.getUserName(), UPDATE_SYSTEMPARAMETER));
		}
		
		return ResponseEntity.ok(message);
	}
}