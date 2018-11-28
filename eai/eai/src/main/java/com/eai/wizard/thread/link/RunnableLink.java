package com.eai.wizard.thread.link;

import com.eai.model.Rule;
import com.eai.wizard.thread.url.DataUrl;

public class RunnableLink implements Runnable {

	private Rule rule;
	private String userAgent;
	private int numberOfRetries;
	
	private DataUrl dataUrl;
	private DataLink dataLink;
	
	public RunnableLink(Rule rule, String userAgent, int numberOfRetries) {
		this.rule = rule;
		this.userAgent = userAgent;
		this.numberOfRetries = numberOfRetries;
		
		dataUrl = DataUrl.getSingletonInstance();
		dataLink = DataLink.getSingletonInstance();
	}

	@Override
	public void run() {
		try {Thread.sleep(5000);} catch (InterruptedException e) { Thread.currentThread().interrupt();}
		
		while(!dataUrl.getUrlList().isEmpty() || dataUrl.getThreadGroupUrl().activeCount() != 0){
			if(dataLink.pool() < 100) {
				String url = dataUrl.getUrlList().poll();
			
				if(url != null) {
					dataLink.link(url, rule, userAgent, numberOfRetries);
					dataLink.plusPool();
				}
			}
		}
		
		dataUrl.getUrlList().clear();
	}
}
