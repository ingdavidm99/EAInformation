package com.eai.wizard.thread.url;

import com.eai.model.Rule;

public class RunnableUrl implements Runnable {

	private Rule rule;
	private String userAgent;
	private int numberOfRetries;
	
	private DataUrl dataUrl;
	
	public RunnableUrl(Rule rule, String userAgent, int numberOfRetries) {
		this.rule = rule;
		this.userAgent = userAgent;
		this.numberOfRetries = numberOfRetries;
		
		dataUrl = DataUrl.getSingletonInstance();
	}

	@Override
	public void run() {
		dataUrl.link(rule, userAgent, numberOfRetries);
	}
}