package com.eai.dto;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.eai.model.LogError;
import com.eai.model.Rule;
import com.eai.model.SystemParameter;
import com.eai.model.User;

public class Pagination {
	
	private long size;
	
	private long page;
	
	private long length;
	
	private long paginaton;
	
	private List<String> data;
	
	private List<SystemParameter> systemParameterList;
	
	private List<LogError> logErrorList;
	
	private List<User> userList;
	
	private List<Rule> ruleList;
	
	public Pagination() {
		data = new LinkedList<>();
		data.add(null);
		data.add(null);
		data.add(null);
		data.add(null);
		data.add(null);
		data.add(null);
		data.add(null);
		
		systemParameterList = new ArrayList<>();
		logErrorList = new ArrayList<>();
		userList = new ArrayList<>();
		ruleList = new ArrayList<>();
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

	public List<SystemParameter> getSystemParameterList() {
		return systemParameterList;
	}

	public void setSystemParameterList(List<SystemParameter> systemParameterList) {
		this.systemParameterList = systemParameterList;
	}

	public List<LogError> getLogErrorList() {
		return logErrorList;
	}

	public void setLogErrorList(List<LogError> logErrorList) {
		this.logErrorList = logErrorList;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	
	public List<Rule> getRuleList() {
		return ruleList;
	}

	public void setRuleList(List<Rule> ruleList) {
		this.ruleList = ruleList;
	}
}