 package com.eai.wizard.serviceimple;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eai.model.Rule;
import com.eai.service.RuleService;
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
	RuleService ruleService;
	
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
	public Rule extractInformation(Integer idRule) {
		
		Rule rule = ruleService.findById(idRule);
		rule = (rule == null) ? new Rule() : rule;
		
		if(idRule > 0 && !"R".equals(rule.getStatus())) {
			
			rule.setStatus("R");
			ruleService.saveOrUpdate(rule);
			
			extractUrl(rule);
			
			extractData(rule);
		}
		
		return rule;
	}
	
	private void extractUrl(Rule rule) {
		if(rule.getUrlRegex() != null) {
			urlService.runnableUrl(rule);
		}
		
		if(rule.getLinkRegex() != null) {
			linkService.runnableLink(rule);
		}
		
		if(rule.getSubLinkRegex() != null) {
			subLinkService.runnableSubLink(rule);
		}
	}
	
	private void extractData(Rule rule) {
		if(rule.getUrlRegex() == null 
				&& rule.getLinkRegex() == null 
				&& rule.getSubLinkRegex() == null) {
			regexService.data(rule.getBaseUrl(), rule.getDescriptionRegex());
			
			rule.setStatus("P");
			ruleService.saveOrUpdate(rule);
		}
		
		if(rule.getUrlRegex() != null 
				&& rule.getLinkRegex() == null 
				&& rule.getSubLinkRegex() == null) {
			urlService.runnableDataUrl(rule);
		}
		
		if(rule.getUrlRegex() != null 
				&& rule.getLinkRegex() != null 
				&& rule.getSubLinkRegex() == null) {
			linkService.runnableDataLink(rule);
		}
		
		if(rule.getUrlRegex() != null 
				&& rule.getLinkRegex() != null 
				&& rule.getSubLinkRegex() != null) {
			subLinkService.runnableDataSubLink(rule);
		}
	}
}