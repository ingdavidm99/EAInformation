package com.eai.wizard.service;

import java.util.Queue;

public interface RegexService {

	public Queue<String> link(String baseUrl, String href, String pagination, boolean isPagination);
	
	public void data(String baseUrl, String rule);
}
