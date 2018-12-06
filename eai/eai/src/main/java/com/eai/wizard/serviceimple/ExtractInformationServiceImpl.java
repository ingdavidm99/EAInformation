 package com.eai.wizard.serviceimple;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eai.model.Rule;
import com.eai.service.SystemParameterService;
import com.eai.wizard.service.ExtractInformationService;
import com.eai.wizard.service.LinkService;
import com.eai.wizard.service.RegexService;
import com.eai.wizard.service.SubLinkService;
import com.eai.wizard.service.UrlService;

@Service
public class ExtractInformationServiceImpl  implements ExtractInformationService{

	@Autowired
	SystemParameterService systemParameterService;
	
	@Autowired
	UrlService urlService;
	
	@Autowired
	LinkService linkService;
	
	@Autowired
	SubLinkService subLinkService;
	
	@Autowired
	RegexService regexService;
		
	/*
	 * {(href)regex}	Para extrare la url de la pagina.
	 * {(!?)regex}		Para la paginacion.
	 * {(tag)regex}		Para extrare la data de la pagina.
	 */
	@Override
	@Transactional
	public void extractInformation(Rule rule) {
       if(rule.getUrlRegex() != null) {
			urlService.runnableUrl(rule);
		}
		
		if(rule.getLinkRegex() != null) {
			linkService.runnableLink(rule);
		}
		
		if(rule.getSubLinkRegex() != null) {
			subLinkService.runnableSubLink(rule);
		}
		
		if(rule.getUrlRegex() == null 
				&& rule.getLinkRegex() == null 
				&& rule.getSubLinkRegex() == null) {
			regexService.data(rule.getBaseUrl(), rule.getDescriptionRegex());
		}
		
		if(rule.getUrlRegex() != null 
				&& rule.getLinkRegex() == null 
				&& rule.getSubLinkRegex() == null) {
			urlService.runnableDataUrl(rule.getDescriptionRegex());
		}
		
		if(rule.getUrlRegex() != null 
				&& rule.getLinkRegex() != null 
				&& rule.getSubLinkRegex() == null) {
			linkService.runnableDataLink(rule.getDescriptionRegex());
		}
		
		if(rule.getUrlRegex() != null 
				&& rule.getLinkRegex() != null 
				&& rule.getSubLinkRegex() != null) {
			subLinkService.runnableDataSubLink(rule.getDescriptionRegex());
		}
	}
}