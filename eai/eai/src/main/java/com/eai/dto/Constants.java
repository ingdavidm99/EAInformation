package com.eai.dto;

public enum Constants {
	
	//STATUS
	FAILURE("F"),
	ERROR("E"),
	SUCCESS("S"),
	
	//VALIDATE
	EMAIL_PATTERN("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"),
	STRING_PATTERN("[a-zA-Z]+"),  
	MOBILE_PATTERN("[0-9]{10}"),
	NUMBER_PATTERN("[0-9]+"),
	NAME_PATTERN("^[A-Za-zÁáÉéÍíÓóÚúÑñüÜ_\\s]+$"),
	
	//GENERAL
	TRANSACTIONPAGE("tp"),
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
