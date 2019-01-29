package com.eai.dto;

import java.math.BigDecimal;

public class Progress {
	private float percentage;
	private int length;
	
	private int count;
	private int countSuccess;
	private int countFail;
	private int countError;
	private String status;
	
    private static volatile Progress progress;

    private Progress() {}

    public static synchronized Progress getSingletonInstance() {
        if (progress == null){
        	progress = new Progress();
        }
        
        return progress;
    }
    
    public synchronized void calculate() {
    	float number = (float)100/length;
    	
    	count += 1;
    	
    	if(count < length)
    	  	percentage += round(number, 10);
    	else
    		percentage = 100;
    }
    
    public synchronized int calculateCountSuccess() {
    	calculate();
    	return countSuccess + 1;
    }
    
    public synchronized int calculateCountFail() {
    	calculate();
    	return countFail + 1;
    }
    
    public synchronized int calculateCountError() {
    	calculate();
    	return countError + 1;
    }
    
    public static float round(float number, int decimalPlace) {
		BigDecimal bd = BigDecimal.valueOf(number);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.floatValue();
	}

	public float getPercentage() {
		return round(percentage, 2);
	}

	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}
	
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCountSuccess() {
		return countSuccess;
	}

	public void setCountSuccess(int countSuccess) {
		this.countSuccess = countSuccess;
	}

	public int getCountFail() {
		return countFail;
	}

	public void setCountFail(int countFail) {
		this.countFail = countFail;
	}
	
	public int getCountError() {
		return countError;
	}

	public void setCountError(int countError) {
		this.countError = countError;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}	
}
