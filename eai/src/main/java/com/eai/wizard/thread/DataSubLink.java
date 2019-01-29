package com.eai.wizard.thread;

import java.util.LinkedList;
import java.util.Queue;

public class DataSubLink {
	
	private ThreadGroup threadGroupSubLink;
	
	private Queue<String> subLinkList;
	
	private int poolSubLink;
	
	private int poolDataSubLink;
	
	private static volatile DataSubLink dataSubLink;

    private DataSubLink() {
    	threadGroupSubLink = new ThreadGroup("threadGroupSubLink");
    	subLinkList = new LinkedList<>();
    	poolSubLink = 0;
    }

    public static synchronized DataSubLink getSingletonInstance() {
        if (dataSubLink == null){
        	dataSubLink = new DataSubLink();
        }
        
        return dataSubLink;
    }
        
    public ThreadGroup getThreadGroupSubLink() {
    	return threadGroupSubLink;
    }
    
    public synchronized Queue<String> getSubLinkList(){
    	return subLinkList;
    }
    
    public synchronized void plusPoolSubLink() {
    	poolSubLink += 1;
    }
    
    public synchronized void lessPoolSubLink() {
    	poolSubLink -= 1;
    }
    
    public synchronized int poolSubLink() {
    	return poolSubLink;
    }
    
    public synchronized void plusPoolDataSubLink() {
    	poolDataSubLink += 1;
    }
    
    public synchronized void lessPoolDataSubLink() {
    	poolDataSubLink -= 1;
    }
    
    public synchronized int poolDataSubLink() {
    	return poolDataSubLink;
    }    
}