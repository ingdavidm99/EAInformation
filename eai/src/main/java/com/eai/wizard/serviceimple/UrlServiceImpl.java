 package com.eai.wizard.serviceimple;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eai.model.Rule;
import com.eai.service.RuleService;
import com.eai.service.SystemParameterService;
import com.eai.wizard.service.RegexService;
import com.eai.wizard.service.UrlService;
import com.eai.wizard.thread.DataUrl;

@Service
public class UrlServiceImpl  implements UrlService{

	@Autowired
	SystemParameterService systemParameterService;
		
	@Autowired
	RegexService regexService;	
	
	@Autowired
	RuleService ruleService;
	
	private DataUrl dataUrl = DataUrl.getSingletonInstance();

	@Override
	@Transactional
	public void runnableUrl(Rule rule) {
		Runnable task = () -> link(rule);
		
		Thread thread = new Thread(task);	
		thread.start();
	}
	
	@Transactional
	private void link(Rule rule){
    	Runnable task = () ->
			dataUrl.getUrlList().addAll(
					regexService.link(
							rule.getBaseUrl(), 
							rule.getUrlRegex(), 
							rule.getPaginationUrlRegex(), 
							rule.isPaginationUrl()));
					
		Thread thread = new Thread(dataUrl.getThreadGroupUrl(), task);	
		thread.start();
    }

	public void runnableDataUrl(Rule rule) {
		int numberOfThreads = Integer.parseInt(systemParameterService.findById(3).getValue());
		
		Runnable task = () -> {
			try {Thread.sleep(10000);} catch (InterruptedException e) { Thread.currentThread().interrupt();}
			
			while(!dataUrl.getUrlList().isEmpty() || dataUrl.getThreadGroupUrl().activeCount() != 0){
				if(dataUrl.poolDataUrl() < numberOfThreads) {
					String url = dataUrl.getUrlList().poll();
					
					if(url != null) {
						data(url, rule);
						dataUrl.plusPoolDataUrl();
					}
				}
			}
			
			rule.setStatus("P");
			ruleService.saveOrUpdate(rule);
		};		
		
		Thread thread = new Thread(task);	
		thread.start();
	}
	
	private void data(String url, Rule rule){
    	Runnable task = () -> {
    		regexService.data(url, rule.getDescriptionRegex());
			dataUrl.lessPoolDataUrl();
		};
		
		Thread thread = new Thread(task);
		thread.start();
	}
}