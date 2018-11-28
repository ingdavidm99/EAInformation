 package com.eai.wizard.serviceimple;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eai.dto.Constants;
import com.eai.model.Rule;
import com.eai.repository.DataRepository;
import com.eai.service.SystemParameterService;
import com.eai.wizard.service.ExtractInformationService;
import com.eai.wizard.thread.link.RunnableDataLink;
import com.eai.wizard.thread.link.RunnableLink;
import com.eai.wizard.thread.sublink.RunnableDataSubLink;
import com.eai.wizard.thread.sublink.RunnableSubLink;
import com.eai.wizard.thread.url.RunnableDataUrl;
import com.eai.wizard.thread.url.RunnableUrl;
import com.eai.wizard.utils.Regex;

@Service
public class ExtractInformationServiceImpl  implements ExtractInformationService{

	@Autowired
	SystemParameterService systemParameterService;
	
	@Autowired
	DataRepository dataRepository;
		
	/*
	 * [(href)regex]	Para extrare la url de la pagina.
	 * [(!?)regex]		Para la paginacion.
	 * [(<tag>)regex]	Para extrare la data de la pagina.
	 */
	@Override
	@Transactional
	public void extractInformation(Rule rule) {
		String userAgent = systemParameterService.findById(6).getValue();
		int numberOfRetries = Integer.parseInt(systemParameterService.findById(2).getValue());
		
		if(!Constants.EMPTY.val().equals(rule.getUrlRegex())) {
			new Thread(new RunnableUrl(rule, userAgent, numberOfRetries)).start();
		}
		
		if(!Constants.EMPTY.val().equals(rule.getLinkRegex())) {
			new Thread(new RunnableLink(rule, userAgent, numberOfRetries)).start();
		}
		
		if(!Constants.EMPTY.val().equals(rule.getSubLinkRegex())) {
			new Thread(new RunnableSubLink(rule, userAgent, numberOfRetries)).start();
		}
		
		if(!Constants.EMPTY.val().equals(rule.getBaseUrl()) &&
				Constants.EMPTY.val().equals(rule.getUrlRegex()) && 
				Constants.EMPTY.val().equals(rule.getLinkRegex()) &&
				Constants.EMPTY.val().equals(rule.getSubLinkRegex())) {
			Regex regex = new Regex(userAgent, numberOfRetries);
			regex.data(rule.getBaseUrl(), rule.getDescriptionRegex(), dataRepository);
		}
		
		if(!Constants.EMPTY.val().equals(rule.getUrlRegex()) && 
				Constants.EMPTY.val().equals(rule.getLinkRegex()) &&
				Constants.EMPTY.val().equals(rule.getSubLinkRegex())) {
			new Thread(new RunnableDataUrl(rule.getDescriptionRegex(), userAgent, numberOfRetries, dataRepository)).start();
		}
		
		if(!Constants.EMPTY.val().equals(rule.getUrlRegex()) && 
				!Constants.EMPTY.val().equals(rule.getLinkRegex()) &&
				Constants.EMPTY.val().equals(rule.getSubLinkRegex())) {
			new Thread(new RunnableDataLink(rule.getDescriptionRegex(), userAgent, numberOfRetries, dataRepository)).start();
		}
		
		if(!Constants.EMPTY.val().equals(rule.getUrlRegex()) &&
				!Constants.EMPTY.val().equals(rule.getLinkRegex()) &&
				!Constants.EMPTY.val().equals(rule.getSubLinkRegex())) {
			new Thread(new RunnableDataSubLink(rule.getDescriptionRegex(), userAgent, numberOfRetries, dataRepository)).start();
		}
	}
}