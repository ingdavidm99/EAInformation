package com.eai.wizard.thread;

import java.util.LinkedList;
import java.util.Queue;

public class DataLink {
	
	private ThreadGroup threadGroupLink;
	
	private Queue<String> linkList;
	
	private int poolLink;
	
	private int poolDataLink;
	
	private static volatile DataLink dataLink;

    private DataLink() {
    	threadGroupLink = new ThreadGroup("threadGroupLink");
    	linkList = new LinkedList<>();
    	poolLink = 0;
    	poolDataLink = 0;
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
    
    public synchronized void plusPoolLink() {
    	poolLink += 1;
    }
    
    public synchronized void lessPoolLink() {
    	poolLink -= 1;
    }
    
    public synchronized int poolLink() {
    	return poolLink;
    }    
    
    public synchronized void plusPoolDataLink() {
    	poolDataLink += 1;
    }
    
    public synchronized void lessPoolDataLink() {
    	poolDataLink -= 1;
    }
    
    public synchronized int poolDataLink() {
    	return poolDataLink;
    }
}