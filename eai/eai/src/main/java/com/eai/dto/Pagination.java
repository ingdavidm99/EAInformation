package com.eai.dto;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.eai.model.LogError;
import com.eai.model.SystemParameters;

public class Pagination {
	
	private long size;
	
	private long page;
	
	private long length;
	
	private long paginaton;
	
	private List<String> data;
	
	private List<SystemParameters> systemParametersList;
	
	private List<LogError> logErrorList;
	
	public Pagination() {
		data = new LinkedList<>();
		data.add(null);
		data.add(null);
		data.add(null);
		data.add(null);
		data.add(null);
		data.add(null);
		data.add(null);
		
		logErrorList = new ArrayList<>();
		systemParametersList = new ArrayList<>();
	}
	
	public void getPage(long querySize, Long pageSize) {
		this.size = (long) Math.ceil((double)querySize/pageSize);
    	
    	page = (page == 0 || page > size) ? 1 : page;
    	
    	this.page = (size == 0) ? 0 : page;
    	this.length = querySize;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public long getPage() {
		return page;
	}

	public void setPage(long page) {
		this.page = page;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public long getPaginaton() {
		return paginaton;
	}

	public void setPaginaton(long paginaton) {
		this.paginaton = paginaton;
	}	
	
	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}

	public List<SystemParameters> getSystemParametersList() {
		return systemParametersList;
	}

	public void setSystemParametersList(List<SystemParameters> systemParametersList) {
		this.systemParametersList = systemParametersList;
	}

	public List<LogError> getLogErrorList() {
		return logErrorList;
	}

	public void setLogErrorList(List<LogError> logErrorList) {
		this.logErrorList = logErrorList;
	}
}