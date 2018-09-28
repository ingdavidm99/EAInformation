package com.eai.dto;

public enum Constants {
	
	//PAGE
	BASEURL("https://www.amazon.com/"),
	ALPHABET("#,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z"),
	REPLACE("[\\|\\*\\/\\?\\\\(\\)\\,\\\\.\\:]"),
	
	PERCENTAGE_GAIN("30"),
	
	//STATUS
	SINGINFAILURE("LF"),
	FAILURE("F"),
	ERROR("E"),
	SUCCESS("S"),
	PENDING("P"),
	
	STARTING("Starting"),		
	FINISHED("Finished"),		
	PROCESSING("Processing..."),	
	
	//VALIDATE
	EMAIL_PATTERN("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"),
	STRING_PATTERN("[a-zA-Z]+"),  
	NUMBER_PATTERN("[0-9]+"),
	STRING_NUMBER_PATTERN("[0-9a-zA-Z]+"),
	MOBILE_PATTERN("[0-9]{10}"),
	NAME_PATTERN("^[A-Za-zÁáÉéÍíÓóÚúÑñüÜ_\\s]+$"),
	DATE_PATTERN("^\\d{4}-\\d{2}-\\d{2}$"),
	
	//GENERAL
	TRANSACTIONPAGE("transactionPage"),
	PAGINATION("pagination"),
	MESSAGESRESPONSE("messagesResponse");
	
	private String val;

	Constants(String val) {
        this.val = val;
    }

    public String val() {
        return val;
    }
}
