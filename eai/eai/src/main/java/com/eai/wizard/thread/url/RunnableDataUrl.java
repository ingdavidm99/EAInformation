package com.eai.wizard.thread.url;

import com.eai.repository.DataRepository;

public class RunnableDataUrl implements Runnable{

	private String rule;
	private String userAgent;
	private int numberOfRetries;
	private DataRepository dataRepository;
	
	private DataUrl dataUrl;
	
	public RunnableDataUrl(String rule, String userAgent, int numberOfRetries, DataRepository dataRepository) {
		this.rule = rule;
		this.userAgent = userAgent;
		this.numberOfRetries = numberOfRetries;
		this.dataRepository = dataRepository;
		
		dataUrl = DataUrl.getSingletonInstance();
	}

	@Override
	public void run() {
		try {Thread.sleep(10000);} catch (InterruptedException e) { Thread.currentThread().interrupt(); }
		
		while(!dataUrl.getUrlList().isEmpty() || dataUrl.getThreadGroupUrl().activeCount() != 0){
			if(dataUrl.poolData() < 100) {
				String url = dataUrl.getUrlList().poll();
				
				if(url != null) {
					dataUrl.data(url, rule, userAgent, numberOfRetries, dataRepository);
					dataUrl.plusPoolData();
				}
			}
		}
	}
}
