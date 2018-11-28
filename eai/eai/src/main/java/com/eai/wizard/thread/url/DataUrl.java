package com.eai.wizard.thread.url;

import java.util.LinkedList;
import java.util.Queue;

import com.eai.model.Rule;
import com.eai.repository.DataRepository;
import com.eai.wizard.utils.Regex;

public class DataUrl {
	
	private ThreadGroup threadGroupUrl;
	
	private Queue<String> urlList;
	
	private int poolData;
	
	private static volatile DataUrl dataUrl;

    private DataUrl() {
    	threadGroupUrl = new ThreadGroup("threadGroupUrl");
    	urlList = new LinkedList<>();
    	poolData = 0;
    }

    public static synchronized DataUrl getSingletonInstance() {
        if (dataUrl == null){
        	dataUrl = new DataUrl();
        }
        
        return dataUrl;
    }
    
    public void link(Rule rule, 
			String userAgent, 
	    	int numberOfRetries){
	    	
		Runnable task = () -> {
		Regex regex = new Regex(userAgent, numberOfRetries);
		
		regex.link(
				rule.getBaseUrl(), 
				rule.getUrlRegex(), 
				rule.getPaginationUrlRegex(), 
				rule.isPaginationUrl(), 
				this.getUrlList());
		};
				
		Thread thread = new Thread(this.getThreadGroupUrl(), task);
		
		thread.start();
	}
	    
	public void data(String url, 
			String rule,
	    	String userAgent, 
	    	int numberOfRetries,
	    	DataRepository dataRepository){
		Runnable task = () -> {
			Regex regex = new Regex(userAgent, numberOfRetries);
			
			regex.data(url, rule, dataRepository);
			this.lessPoolData();
		};
		
		Thread thread = new Thread(task);
		
		thread.start();
	}
        
    public ThreadGroup getThreadGroupUrl() {
    	return threadGroupUrl;
    }
    
    public synchronized Queue<String> getUrlList(){
    	return urlList;
    }
    
    public synchronized void plusPoolData() {
    	poolData += 1;
    }
    
    public synchronized void lessPoolData() {
    	poolData -= 1;
    }
    
    public synchronized int poolData() {
    	return poolData;
    }    
}