package com.eai.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.validation.ObjectError;

import com.eai.model.SystemParameters;

public class MessageResponse implements Serializable{
	
    private static final long serialVersionUID = 3407092064428807228L;
    
	private String status;
    
    private int code;
    
    private SystemParameters data;
    
    private List<ObjectError> errors;
        
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
	
	public Object getData() {
		return data;
	}

	public void setData(SystemParameters data) {
		this.data = data;
	}

	public List<ObjectError> getErrors() {
		return errors;
	}

	public void setErrors(List<ObjectError> errors) {
		this.errors = errors;
	}
}
