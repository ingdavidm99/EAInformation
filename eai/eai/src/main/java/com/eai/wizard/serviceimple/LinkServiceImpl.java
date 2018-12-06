 package com.eai.wizard.serviceimple;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eai.model.Rule;
import com.eai.repository.DataRepository;
import com.eai.service.SystemParameterService;
import com.eai.wizard.service.LinkService;
import com.eai.wizard.service.RegexService;
import com.eai.wizard.thread.DataLink;
import com.eai.wizard.thread.DataUrl;

@Service
public class LinkServiceImpl  implements LinkService{

	@Autowired
	SystemParameterService systemParameterService;
	
	@Autowired
	DataRepository dataRepository;
	
	@Autowired
	RegexService regexService;
	
	private DataUrl dataUrl = DataUrl.getSingletonInstance();
	private DataLink dataLink = DataLink.getSingletonInstance();
	
	@Override
	@Transactional
	public void runnableLink(Rule rule) {
		int numberOfThreads = Integer.parseInt(systemParameterService.findById(3).getValue());
		
		Runnable task = () -> {
			while(!dataUrl.getUrlList().isEmpty() || dataUrl.getThreadGroupUrl().activeCount() != 0){
				if(dataLink.poolLink() < numberOfThreads) {
					String url = dataUrl.getUrlList().poll();
				
					if(url != null) {
						link(url, rule);
						dataLink.plusPoolLink();
					}
				}
			}
			
			dataUrl.getUrlList().clear();
		};
		
		try {Thread.sleep(5000);} catch (InterruptedException e) { Thread.currentThread().interrupt();}
		Thread thread = new Thread(task);	
		thread.start();
	}
	
	@Transactional
	private void link(String url, Rule rule){
    	Runnable task = () -> {
			dataLink.getLinkList().addAll(
					regexService.link(
								url, 
								rule.getLinkRegex(), 
								rule.getPaginationLinkRegex(), 
								rule.isPaginationLink()));
			
			dataLink.lessPoolLink();
		};
		
		Thread thread = new Thread(dataLink.getThreadGroupLink(), task);
		thread.start();
    }

	public void runnableDataLink(String rule) {
		int numberOfThreads = Integer.parseInt(systemParameterService.findById(3).getValue());
		
		Runnable task = () -> {
			while(!dataLink.getLinkList().isEmpty() || dataLink.getThreadGroupLink().activeCount() != 0){
				if(dataLink.poolDataLink() < numberOfThreads) {
					String url = dataLink.getLinkList().poll();
					
					if(url != null) {
						data(url, rule);
						dataLink.plusPoolDataLink();
					}
				}
			}
		};
		
		try {Thread.sleep(10000);} catch (InterruptedException e) { Thread.currentThread().interrupt(); }
		Thread thread = new Thread(task);	
		thread.start();
	}
	
	private void data(String url, String rule){
    	Runnable task = () -> {
			regexService.data(url, rule);
			dataLink.lessPoolDataLink();
		};
		
		Thread thread = new Thread(task);
		thread.start();
	}
}