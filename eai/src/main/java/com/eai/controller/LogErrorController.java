package com.eai.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eai.dto.Constants;
import com.eai.dto.MessageResponse;
import com.eai.dto.Pagination;
import com.eai.dto.TransactionPage;
import com.eai.model.LogError;
import com.eai.service.LogErrorService;
import com.eai.validator.SearchLogErrorValidator;

@Controller
@Scope("prototype")
public class LogErrorController {
	
	MessageResponse message = new MessageResponse();
	
	TransactionPage transactionPage;
	
	@Autowired
	LogErrorService logErrorService;
		
	public static final String LOGERROR = "/logerror";
	public static final String SEARCH_LOGERROR = "/searchLogerror";
	
	@InitBinder("Pagination")
	protected void setupBinder(WebDataBinder binder, HttpServletRequest request) {
		binder.addValidators(new SearchLogErrorValidator(TransactionPage.getData(request)));
	}
	
	@RequestMapping(path = LOGERROR, method = RequestMethod.GET)
    public String page(Model model, HttpServletRequest request) {
		try {
			transactionPage = TransactionPage.getData(request, LOGERROR);
		} catch (Exception exception) {
			message = logErrorService.save(new LogError(exception, transactionPage.getUserName(), LOGERROR));
	    }
		
		model.addAttribute(Constants.TRANSACTIONPAGE.val(), transactionPage);
		model.addAttribute(Constants.PAGINATION.val(), new Pagination());
		model.addAttribute(Constants.MESSAGESRESPONSE.val(), message);
		
    	return  LOGERROR;
	}
	
	@RequestMapping(path = SEARCH_LOGERROR, method = RequestMethod.POST)
    public String searchLogerror(
    		Model model,
    		HttpServletRequest request,
    		@ModelAttribute("Pagination") @Valid Pagination pagination,
    		BindingResult bindingResult) {
		
        try {
        	transactionPage = TransactionPage.getData(request, LOGERROR);
        	
        	if (bindingResult.hasErrors()) {
        		for(FieldError error : bindingResult.getFieldErrors()){
        			model.addAttribute(error.getField().replace("[","_").replace("]",""), error.getDefaultMessage());
				}
        		
        		message.setStatus(Constants.FAILURE.val());
			 }else {
				 logErrorService.findAll(pagination, transactionPage.getPageSize());
			 }
        } catch (Exception exception) {
        	message = logErrorService.save(new LogError(exception, transactionPage.getUserName(), SEARCH_LOGERROR));
        }
                
    	model.addAttribute(Constants.TRANSACTIONPAGE.val(), transactionPage); 
    	model.addAttribute(Constants.PAGINATION.val(), pagination); 
        model.addAttribute(Constants.MESSAGESRESPONSE.val(), message);
        
        return  LOGERROR;
	}
}