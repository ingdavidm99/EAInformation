package com.eai.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.ObjectError;

public class MessageResponse implements Serializable{
	
    private static final long serialVersionUID = 3407092064428807228L;
    
	private String status;
    
    private int code;
    
    private List<ObjectError> errors;
    
    private Map<String, Object> data;
        
    public MessageResponse() {
    	data = new HashMap<>();
	}

	public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public List<ObjectError> getErrors() {
		return errors;
	}

	public void setErrors(List<ObjectError> errors) {
		this.errors = errors;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(String key, Object value) {
		this.data.put(key, value);
	}
}
