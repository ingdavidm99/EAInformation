package com.eai.wizard.page;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.eai.model.LogError;
import com.eai.service.LogErrorService;

public class Page {
	
    public Element element(String url, String match, int numberOfRetries, String userAgent){
        Element element = null;
    	Document document = null;
        int tries = 0;
        
        while (tries <  numberOfRetries) {
        	try {
        		if(userAgent == null)
        			document = Jsoup
        						.connect(url)
        						.get();
        		else
        			document = Jsoup
	    						.connect(url)
	    						.userAgent(userAgent)
	    						.get();
        		
        		element = document.getElementById(match);
        		
        		tries += 1;
        		
        		if(element != null)
        			break;
        		
        		Thread.sleep(500);
        	}catch (Exception e) {
        		tries += 1;
        		element = null;
			}
        }
        
        return element;
    }
    
    public BigDecimal convertTo(LogErrorService logErrorService, String userName, int numberOfRetries) {
    	BigDecimal price = BigDecimal.ZERO;
    	try {
    		Element firstDiv = element("https://www.google.com.co/search?source=&q=1+usd+to+cop&oq=1", "knowledge-currency__tgt-input", numberOfRetries, null);
			String priceStr =  firstDiv.attr("value");
			price = new BigDecimal(priceStr);
		} catch (Exception exception) {
			logErrorService.save(new LogError(exception, userName, "convertTo"));
		}
    	
    	return price.setScale(2, RoundingMode.DOWN);
    }
}
