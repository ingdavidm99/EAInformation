package com.eai.wizard.thread;

import java.util.LinkedList;
import java.util.Queue;

public class DataUrl {
	
	private ThreadGroup threadGroupUrl;
	
	private Queue<String> urlList;
	
	private int poolDataUrl;
	
	private static volatile DataUrl dataUrl;

    private DataUrl() {
    	threadGroupUrl = new ThreadGroup("threadGroupUrl");
    	urlList = new LinkedList<>();
    	poolDataUrl = 0;
    }

    public static synchronized DataUrl getSingletonInstance() {
        if (dataUrl == null){
        	dataUrl = new DataUrl();
        }
        
        return dataUrl;
    }	
        
    public ThreadGroup getThreadGroupUrl() {
    	return threadGroupUrl;
    }
    
    public synchronized Queue<String> getUrlList(){
    	return urlList;
    }
    
    public synchronized void plusPoolDataUrl() {
    	poolDataUrl += 1;
    }
    
    public synchronized void lessPoolDataUrl() {
    	poolDataUrl -= 1;
    }
    
    public synchronized int poolDataUrl() {
    	return poolDataUrl;
    }    
}