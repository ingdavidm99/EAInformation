package com.eai.dto;

public enum Constants {
	
	EMPTY(""),
	
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
	
	STARTING("7_starting"),		
	FINISHED("7_finished"),		
	PROCESSING("7_processing"),	
	
	//VALIDATE
	
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
