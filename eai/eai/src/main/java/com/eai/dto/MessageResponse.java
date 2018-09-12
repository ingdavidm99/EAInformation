package com.eai.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.validation.ObjectError;

public class MessageResponse implements Serializable{
	
    private static final long serialVersionUID = 3407092064428807228L;
    
	private String status;
    
    private Integer code;
    
    private List<ObjectError> errors;
        
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public List<ObjectError> getErrors() {
		return errors;
	}

	public void setErrors(List<ObjectError> errors) {
		this.errors = errors;
	}
}
