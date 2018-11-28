package com.eai.wizard.utils;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Page {
	
	public Document response(String url, String userAgent, int numberOfRetries){
		Response response;
		int tries = 0;
        
        while (tries <  numberOfRetries) {
        	try {
				response = Jsoup.connect(url).userAgent(userAgent).followRedirects(false).execute();
					
				tries += 1;
				if(response.statusCode() == 200) {
					return response.parse();
				}	
				
				Thread.sleep(500);
	    	}catch (Exception e) {
	    		return null;
			}
        }
        
		return null;
    }
}
