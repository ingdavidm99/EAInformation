package com.eai.dto;

import java.util.List;

import com.eai.model.SystemParameters;

public class Pagination {
	
	private String search;
	
	private long size;
	
	private long page;
	
	private long length;
	
	private long paginaton;
	
	private List<SystemParameters> result;

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
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

	public List<SystemParameters> getResult() {
		return result;
	}

	public void setResult(List<SystemParameters> result) {
		this.result = result;
	}
}