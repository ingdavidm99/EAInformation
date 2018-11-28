package com.eai.wizard.thread.sublink;

import java.util.LinkedList;
import java.util.Queue;

import com.eai.model.Rule;
import com.eai.repository.DataRepository;
import com.eai.wizard.utils.Regex;

public class DataSubLink {
	
	private ThreadGroup threadGroupSubLink;
	
	private Queue<String> subLinkList;
	
	private int pool;
	
	private int poolData;
	
	private static volatile DataSubLink dataSubLink;

    private DataSubLink() {
    	threadGroupSubLink = new ThreadGroup("threadGroupSubLink");
    	subLinkList = new LinkedList<>();
    	pool = 0;
    }

    public static synchronized DataSubLink getSingletonInstance() {
        if (dataSubLink == null){
        	dataSubLink = new DataSubLink();
        }
        
        return dataSubLink;
    }
    
    public void link(String url, 
    		Rule rule, 
    		String userAgent, 
    		int numberOfRetries){
    	
		Runnable task = () -> {
			Regex regex = new Regex(userAgent, numberOfRetries);
			
			regex.link(
					url, 
					rule.getSubLinkRegex(), 
					rule.getPaginationSubLinkRegex(), 
					rule.isPaginationSubLink(), 
					this.getSubLinkList());
			
			this.lessPool();
		};
			
		Thread thread = new Thread(this.getThreadGroupSubLink(), task);
		
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
        
    public ThreadGroup getThreadGroupSubLink() {
    	return threadGroupSubLink;
    }
    
    public synchronized Queue<String> getSubLinkList(){
    	return subLinkList;
    }
    
    public synchronized void plusPool() {
    	pool += 1;
    }
    
    public synchronized void lessPool() {
    	pool -= 1;
    }
    
    public synchronized int pool() {
    	return pool;
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