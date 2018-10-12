package com.eai.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eai.dto.Constants;
import com.eai.dto.MessageResponse;
import com.eai.dto.Progress;
import com.eai.dto.TransactionPage;
import com.eai.model.LogError;
import com.eai.service.LogErrorService;
import com.eai.service.SystemParametersService;
import com.eai.wizard.model.LevelOne;
import com.eai.wizard.model.ViewLevelThree;
import com.eai.wizard.model.ViewLevelTwo;
import com.eai.wizard.page.Page;
import com.eai.wizard.service.LevelFourService;
import com.eai.wizard.service.LevelOneService;
import com.eai.wizard.service.LevelThreeService;
import com.eai.wizard.service.LevelTwoService;
import com.eai.wizard.thread.ThradLevelOne;
import com.eai.wizard.thread.ThradLevelThree;
import com.eai.wizard.thread.ThradLevelTwo;

@Controller
public class EAinformationController {
	
	MessageResponse message = new MessageResponse();
	
	TransactionPage transactionPage;
		
	@Autowired
	LogErrorService logErrorService;
	
	@Autowired
	SystemParametersService systemParametersService;
	
	@Autowired
	LevelOneService levelOneService;
	
	@Autowired
	LevelTwoService levelTwoService;
	
	@Autowired
	LevelThreeService levelThreeService;
	
	@Autowired
	LevelFourService levelFourService;
	
	public static final String PATTH_EAINFORMATION = "/eainformation";
	public static final String PATTH_PROGRESS = "/progress";
	public static final String PATTH_STEP1 = "/step1";
	public static final String PATTH_STEP2 = "/step2";
	public static final String PATTH_STEP3 = "/step3";
	public static final String PATTH_STEP4 = "/step4";
	
	@RequestMapping(path = PATTH_EAINFORMATION, method = RequestMethod.GET)
    public String page(Model model, HttpServletRequest request) {
		try {
			transactionPage = TransactionPage.getData(request, PATTH_EAINFORMATION);
		} catch (Exception exception) {
			message = logErrorService.save(new LogError(exception, transactionPage.getUserName(), PATTH_EAINFORMATION));
	    }
		
		model.addAttribute(Constants.TRANSACTIONPAGE.val(), transactionPage);
		model.addAttribute(Constants.MESSAGESRESPONSE.val(), message);
		
    	return  PATTH_EAINFORMATION;
	}
	
	@RequestMapping(path = PATTH_STEP1, method = RequestMethod.POST)
	public ResponseEntity<Object> step1(Model model, HttpServletRequest request) {
		Progress progress = Progress.getSingletonInstance();
		progressStarting(progress);
		
		try {
			transactionPage = TransactionPage.getData(request, PATTH_EAINFORMATION);
			
			ThradLevelOne thradLevelOne;
			List<LevelOne> levelOneList = levelOneService.findPendingOrFail();
			
			int length = (levelOneList.isEmpty())? Constants.ALPHABET.val().split(",").length : levelOneList.size();
			
			int successLength = levelOneService.findSuccess();
			
			progress.setLength(length);
			
			if(levelOneList.isEmpty() && successLength != Constants.ALPHABET.val().split(",").length) {
				for(String abc : Constants.ALPHABET.val().split(",")){
		        	thradLevelOne = new ThradLevelOne(new LevelOne(abc), levelOneService);
		            thradLevelOne.start();
		            thradLevelOne.join();
		        }        
			}else {
				if(Constants.ALPHABET.val().split(",").length == successLength) {
					progressEnding(progress, length);			
				}else {
					for(LevelOne levelOne : levelOneList){
			        	thradLevelOne = new ThradLevelOne(levelOne, levelOneService);
			            thradLevelOne.start();
			        }
				}
			}
			
		} catch (Exception exception) {
			message = logErrorService.save(new LogError(exception, transactionPage.getUserName(), PATTH_STEP1));
        }
		
		return ResponseEntity.ok(message);
	}
	
	@RequestMapping(path = PATTH_STEP2, method = RequestMethod.POST)
    public ResponseEntity<Object> step2(Model model , HttpServletRequest request) {
		
		Progress progress = Progress.getSingletonInstance();
		progressStarting(progress);
		
		try {
			transactionPage = TransactionPage.getData(request, PATTH_EAINFORMATION);
			
			int numberOfRetries = Integer.parseInt(systemParametersService.findById(2).getValue());
			String userAgent = systemParametersService.findById(6).getValue();
			
			List<LevelOne> levelOneList = levelOneService.findPendingOrFail();
	        
			if(!levelOneList.isEmpty()) {
				int length = levelOneList.size();
				
				progress.setLength(length);
		        
		        ThradLevelTwo thradLevelTwo;
		        for(LevelOne levelOne : levelOneList){
		        	thradLevelTwo = new 
		        			ThradLevelTwo(
		        					levelOne, 
		        					levelTwoService, 
		        					userAgent,
		        					numberOfRetries);
		        	
		        	thradLevelTwo.start();
		        }
	        }else {
	        	int successLength = Constants.ALPHABET.val().split(",").length;
	        	progressEnding(progress, successLength);
	        }
		 } catch (Exception exception) {
			 message = logErrorService.save(new LogError(exception, transactionPage.getUserName(), PATTH_STEP2));
         }
        
        return ResponseEntity.ok(message);
	}
	
	@RequestMapping(path = PATTH_STEP3, method = RequestMethod.POST)
    public ResponseEntity<Object> step3(Model model, HttpServletRequest request) {
		
		Progress progress = Progress.getSingletonInstance();
		progressStarting(progress);
		
		try {
			transactionPage = TransactionPage.getData(request, PATTH_EAINFORMATION);
			
			int numberOfRetries = Integer.parseInt(systemParametersService.findById(2).getValue());
			int numberOfThreads = Integer.parseInt(systemParametersService.findById(3).getValue());
			String userAgent = systemParametersService.findById(6).getValue();
			
			int levelTwoLength = levelTwoService.findPendingOrFail();
			
			if(levelTwoLength > 0) {
				
		        List<ThradLevelThree> thradLevelThreeList = new ArrayList<>();
		        ThradLevelThree thradLevelThree;
		        List<ViewLevelTwo> viewLevelTwoList;
		        int numberThreads = (levelTwoLength > 40)? numberOfThreads : 1;
		        int from = 0;
		        int to = (int)Math.ceil((float)levelTwoLength/numberThreads);
		        boolean valid = true;
		        
		        while(valid) {
		        	viewLevelTwoList = levelTwoService.findPendingOrFail(from, to);
		        	
		        	if(viewLevelTwoList.isEmpty()) {
		        		progress.setLength(levelTwoLength);
		        		valid = false;
		        	}else {
		        		progress.setLength(from);
		        		
		        		thradLevelThree = new 
		        				ThradLevelThree(
		        						viewLevelTwoList, 
		        						levelThreeService, 
		        						userAgent,
		        						numberOfRetries);
		        		
			        	thradLevelThreeList.add(thradLevelThree);
		        	}
		        	
		        	from += to;
		        }
		        
		        for (ThradLevelThree th : thradLevelThreeList) {
	            	th.start();
		        }
	        }else {
	        	int successLength = levelTwoService.findSuccess();
	        	progressEnding(progress, successLength);
	        }
		 } catch (Exception exception) {
			 message = logErrorService.save(new LogError(exception, transactionPage.getUserName(), PATTH_STEP3));
         }
        
		return ResponseEntity.ok(message);
	}
	
	@RequestMapping(path = PATTH_STEP4, method = RequestMethod.POST)
    public ResponseEntity<Object> step4(Model model, HttpServletRequest request) {
		
		Progress progress = Progress.getSingletonInstance();
		progressStarting(progress);
		
		try {
			transactionPage = TransactionPage.getData(request, PATTH_EAINFORMATION);
			
			int numberOfRetries = Integer.parseInt(systemParametersService.findById(2).getValue());
			
			Page page = new Page();
			
			BigDecimal shippingUsd = new BigDecimal(systemParametersService.findById(4).getValue());
			BigDecimal shippingCop = new BigDecimal(systemParametersService.findById(5).getValue());
			BigDecimal usdToCop = page.convertTo(logErrorService, transactionPage.getUserName(), numberOfRetries);
			
			List<ViewLevelThree> viewLevelThreeList = levelThreeService.findPendingOrFail();
	        
			if(!viewLevelThreeList.isEmpty()) {
				progress.setLength(viewLevelThreeList.size());
		        
		        levelFourService.saveAll(viewLevelThreeList, usdToCop, shippingUsd, shippingCop);
	        }else {
	        	int successLength = levelThreeService.findSuccess();
	        	progressEnding(progress, successLength);
	        }
		 }catch (Exception exception) {
			 message = logErrorService.save(new LogError(exception, transactionPage.getUserName(), PATTH_STEP4));
         }
        
		return ResponseEntity.ok(message);
	}
	
	@RequestMapping(path = PATTH_PROGRESS, method = RequestMethod.POST)
    public ResponseEntity<Object> progress(Model model, HttpServletRequest request) {
		
        Progress progress = Progress.getSingletonInstance();
       
        try {
        	transactionPage = TransactionPage.getData(request, PATTH_EAINFORMATION);
        	String mens = "";
        	
        	switch (""+progress.getPercentage()) {
				case "100.0":
					mens = transactionPage.get(Constants.FINISHED.val());
				break;
				case "0.0":
					mens = transactionPage.get(Constants.STARTING.val());
				break;
				default:
					mens = transactionPage.get(Constants.PROCESSING.val());
				break;
			}
        	
	        progress.setStatus(mens);
        } catch (Exception exception) {
        	message = logErrorService.save(new LogError(exception, transactionPage.getUserName(), PATTH_PROGRESS));
        	return  ResponseEntity.ok(message);
        }
        
        return  ResponseEntity.ok(progress);
	}
	
	public void progressStarting(Progress progress) {
		progress.setPercentage(0);
        progress.setLength(1);
        progress.setCount(0);
        progress.setCountSuccess(0);
        progress.setCountFail(0);
        progress.setCountError(0);
        progress.setStatus(transactionPage.get(Constants.STARTING.val()));
	}
	
	public void progressEnding(Progress progress, int length) {
		progress.setPercentage(100);
        progress.setLength(length);
        progress.setCount(length);
        progress.setCountSuccess(length);
        progress.setCountFail(0);
        progress.setCountError(0);
        progress.setStatus(transactionPage.get(Constants.FINISHED.val()));
	}
}