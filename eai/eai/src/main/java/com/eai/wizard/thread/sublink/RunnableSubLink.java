package com.eai.wizard.thread.sublink;

import com.eai.model.Rule;
import com.eai.wizard.thread.link.DataLink;

public class RunnableSubLink implements Runnable {

	private Rule rule;
	private String userAgent;
	private int numberOfRetries;
	
	private DataLink dataLink;
	private DataSubLink dataSubLink;
	
	public RunnableSubLink(Rule rule, String userAgent, int numberOfRetries) {
		this.rule = rule;
		this.userAgent = userAgent;
		this.numberOfRetries = numberOfRetries;
		
		dataLink = DataLink.getSingletonInstance();
		dataSubLink = DataSubLink.getSingletonInstance();
	}

	@Override
	public void run() {
		try {Thread.sleep(5000);} catch (InterruptedException e) { Thread.currentThread().interrupt();}
		
		while(!dataLink.getLinkList().isEmpty() || dataLink.getThreadGroupLink().activeCount() != 0){
			if(dataSubLink.pool() < 100) {
				String url = dataLink.getLinkList().poll();
				
				if(url != null) {
					dataSubLink.link(url, rule, userAgent, numberOfRetries);
					dataSubLink.plusPool();
				}
			}
		}
		
		dataLink.getLinkList().clear();
	}
}
