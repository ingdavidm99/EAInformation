package com.eai.wizard.thread.sublink;

import com.eai.repository.DataRepository;

public class RunnableDataSubLink implements Runnable{

	private String rule;
	private String userAgent;
	private int numberOfRetries;
	private DataRepository dataRepository;
	
	private DataSubLink dataSubLink;
	
	public RunnableDataSubLink(String rule, String userAgent, int numberOfRetries, DataRepository dataRepository) {
		this.rule = rule;
		this.userAgent = userAgent;
		this.numberOfRetries = numberOfRetries;
		this.dataRepository = dataRepository;
		
		dataSubLink = DataSubLink.getSingletonInstance();
	}

	@Override
	public void run() {
		try {Thread.sleep(10000);} catch (InterruptedException e) { Thread.currentThread().interrupt(); }
		
		while(!dataSubLink.getSubLinkList().isEmpty() || dataSubLink.getThreadGroupSubLink().activeCount() != 0){
			if(dataSubLink.poolData() < 100) {
				String url = dataSubLink.getSubLinkList().poll();
				
				if(url != null) {
					dataSubLink.data(url, rule, userAgent, numberOfRetries, dataRepository);
					dataSubLink.plusPoolData();
				}
			}
		}
	}
}
