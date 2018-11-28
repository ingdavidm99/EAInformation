package com.eai.wizard.thread.link;

import com.eai.repository.DataRepository;

public class RunnableDataLink implements Runnable{

	private String rule;
	private String userAgent;
	private int numberOfRetries;
	private DataRepository dataRepository;
	
	private DataLink dataLink;
	
	public RunnableDataLink(String rule, String userAgent, int numberOfRetries, DataRepository dataRepository) {
		this.rule = rule;
		this.userAgent = userAgent;
		this.numberOfRetries = numberOfRetries;
		this.dataRepository = dataRepository;
		
		dataLink = DataLink.getSingletonInstance();
	}

	@Override
	public void run() {
		try {Thread.sleep(10000);} catch (InterruptedException e) { Thread.currentThread().interrupt(); }
		
		while(!dataLink.getLinkList().isEmpty() || dataLink.getThreadGroupLink().activeCount() != 0){
			if(dataLink.poolData() < 100) {
				String url = dataLink.getLinkList().poll();
				
				if(url != null) {
					dataLink.data(url, rule, userAgent, numberOfRetries, dataRepository);
					dataLink.plusPoolData();
				}
			}
		}
	}
}
