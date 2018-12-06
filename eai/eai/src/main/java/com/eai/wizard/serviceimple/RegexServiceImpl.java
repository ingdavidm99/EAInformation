package com.eai.wizard.serviceimple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eai.model.Data;
import com.eai.repository.DataRepository;
import com.eai.service.SystemParameterService;
import com.eai.wizard.service.RegexService;
import com.eai.wizard.utils.Page;
import com.eai.wizard.utils.Xml;

@Service
public class RegexServiceImpl implements RegexService{
	
	@Autowired
	SystemParameterService systemParameterService;
	
	@Autowired
	DataRepository dataRepository;
	
	@Override
	@Transactional
	public Queue<String> link(String baseUrl, String href, String pagination, boolean isPagination){
		Page page = new Page();
		Queue<String> data = new LinkedList<>();
		String userAgent = systemParameterService.findById(6).getValue();
		int numberOfRetries = Integer.parseInt(systemParameterService.findById(2).getValue());
		
		String url = null;
		String match = href(href);
		pagination = (isPagination)? pagination(pagination) : "";
		
		boolean statusCode = true;
		int index = 1;
		
		while(statusCode) {
			if(index != 1 && isPagination) 
				url = baseUrl + pagination.replace("{0}", "" + index);
			else 
				url = baseUrl;
			
			Document document = page.response(url, userAgent, numberOfRetries);
			
			if(document != null) {
				data.addAll(document.select(match).eachAttr("href"));
			}else {
				statusCode = false;
			}
			
			if(!isPagination)
				statusCode = false;
			else
				index +=1;
		}
		
		return data;
	}
	
	private String href(String text) {
		String patternString = "\\[\\(href\\)(.*?)\\]";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);

        StringBuilder cadena = new StringBuilder();
        while(matcher.find()) {
            cadena.append(matcher.group(1)).append(",");
        }
	        
       return cadena.substring(0, cadena.length()-1);
	}
	
	private String pagination(String text) {
		String patternString = "\\[\\(!\\?\\)(.*?)\\]";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);

        StringBuilder cadena = new StringBuilder();
        while(matcher.find()) {
            cadena.append(matcher.group(1));
        }
	        
       return cadena.toString();
	}

	@Override
	@Transactional
	public void data(String baseUrl, String rule ){
		Page page = new Page();
		List<Map<String, String>> match = tag(rule);
		String userAgent = systemParameterService.findById(6).getValue();
		int numberOfRetries = Integer.parseInt(systemParameterService.findById(2).getValue());
		
		Document document = page.response(baseUrl, userAgent, numberOfRetries);
			
		if(document != null) {
			Pattern pattern = null;
			Matcher matcher = null;
			
			List<String> key = new ArrayList<>();
			List<String> value = new ArrayList<>();
			 
			for (Map.Entry<String,String> e : match.get(0).entrySet()) {
				pattern = Pattern.compile(e.getValue());
				matcher = pattern.matcher(document.outerHtml());
				
				while(matcher.find()) {
					key.add(e.getKey());
					value.add(matcher.group(1).trim());
				}
			}
			
			if (!key.isEmpty() || !value.isEmpty() || key.size() != value.size()) {
				key.add("url");
				value.add(baseUrl);
				
				Xml xml = new Xml();
				Data data = new Data();
				
				data.setXml(xml.generate(key, value));
				dataRepository.save(data);
			}
		}
	}
	
	private List<Map<String, String>> tag(String text) {
		String patternString = "\\{\\((.*?)\\)(.*?)\\}";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);

        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        while(matcher.find()) {
        	map.put(matcher.group(1), matcher.group(2));
        	list.add(map);
        }
	        
       return list;
	}
}
