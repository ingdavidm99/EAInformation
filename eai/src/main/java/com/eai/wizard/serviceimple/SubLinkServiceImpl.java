 package com.eai.wizard.serviceimple;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eai.model.Rule;
import com.eai.service.RuleService;
import com.eai.service.SystemParameterService;
import com.eai.wizard.service.RegexService;
import com.eai.wizard.service.SubLinkService;
import com.eai.wizard.thread.DataLink;
import com.eai.wizard.thread.DataSubLink;

@Service
public class SubLinkServiceImpl  implements SubLinkService{

	@Autowired
	SystemParameterService systemParameterService;
	
	@Autowired
	RegexService regexService;
	
	@Autowired
	RuleService ruleService;
	
	private DataLink dataLink = DataLink.getSingletonInstance();
	private DataSubLink dataSubLink = DataSubLink.getSingletonInstance();
	
	@Override
	@Transactional
	public void runnableSubLink(Rule rule) {
		int numberOfThreads = Integer.parseInt(systemParameterService.findById(3).getValue());
		
		Runnable task = () -> {
			try {Thread.sleep(5000);} catch (InterruptedException e) { Thread.currentThread().interrupt();}
			
			while(!dataLink.getLinkList().isEmpty() || dataLink.getThreadGroupLink().activeCount() != 0){
				if(dataSubLink.poolSubLink() < numberOfThreads) {
					String url = dataLink.getLinkList().poll();
					
					if(url != null) {
						link(url, rule);
						dataSubLink.plusPoolSubLink();
					}
				}
			}
			
			dataLink.getLinkList().clear();
		};
		
		Thread thread = new Thread(task);	
		thread.start();
	}
	
	@Transactional
	private void link(String url, Rule rule){
    	Runnable task = () -> {
			dataSubLink.getSubLinkList().addAll(
					regexService.link(
							url, 
							rule.getSubLinkRegex(), 
							rule.getPaginationSubLinkRegex(), 
							rule.isPaginationSubLink()));
			
			dataSubLink.lessPoolSubLink();
		};
			
		Thread thread = new Thread(dataSubLink.getThreadGroupSubLink(), task);
		thread.start();
    }

	public void runnableDataSubLink(Rule rule) {
		int numberOfThreads = Integer.parseInt(systemParameterService.findById(3).getValue());
		
		Runnable task = () -> {
			try {Thread.sleep(10000);} catch (InterruptedException e) { Thread.currentThread().interrupt(); }
			
			while(!dataSubLink.getSubLinkList().isEmpty() || dataSubLink.getThreadGroupSubLink().activeCount() != 0){
				if(dataSubLink.poolDataSubLink() < numberOfThreads) {
					String url = dataSubLink.getSubLinkList().poll();
					
					if(url != null) {
						data(url, rule);
						dataSubLink.plusPoolDataSubLink();
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
			dataSubLink.lessPoolDataSubLink();
		};
		
		Thread thread = new Thread(task);
		thread.start();
	}
}