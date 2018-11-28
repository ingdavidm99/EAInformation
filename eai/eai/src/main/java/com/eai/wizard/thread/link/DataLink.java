package com.eai.wizard.thread.link;

import java.util.LinkedList;
import java.util.Queue;

import com.eai.model.Rule;
import com.eai.repository.DataRepository;
import com.eai.wizard.utils.Regex;

public class DataLink {
	
	private ThreadGroup threadGroupLink;
	
	private Queue<String> linkList;
	
	private int pool;
	
	private int poolData;
	
	private static volatile DataLink dataLink;

    private DataLink() {
    	threadGroupLink = new ThreadGroup("threadGroupLink");
    	linkList = new LinkedList<>();
    	pool = 0;
    	poolData = 0;
    }
    
    public void link(String url,
    		Rule rule, 
    		String userAgent, 
    		int numberOfRetries){
    	
		Runnable task = () -> {
			Regex regex = new Regex(userAgent, numberOfRetries);
			
			regex.link(
					url, 
					rule.getLinkRegex(), 
					rule.getPaginationLinkRegex(), 
					rule.isPaginationLink(), 
					this.getLinkList());
			
			this.lessPool();
		};
		
		Thread thread = new Thread(this.getThreadGroupLink(), task);
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

    public static synchronized DataLink getSingletonInstance() {
        if (dataLink == null){
        	dataLink = new DataLink();
        }
        
        return dataLink;
    }
    
    public ThreadGroup getThreadGroupLink() {
    	return threadGroupLink;
    }
    
    public synchronized Queue<String> getLinkList(){
    	return linkList;
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