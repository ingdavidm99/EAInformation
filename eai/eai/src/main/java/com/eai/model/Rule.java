package com.eai.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rule")
public class Rule implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RULE")
    private Integer idRule;
	
	@Column(name = "CODE")
    private String code;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "BASE_URL")
	private String baseUrl;
	
	@Column(name = "URL_REGEX")
	private String urlRegex;
	
	@Column(name = "PAGINATION_URL")	
	private boolean paginationUrl;
	
	@Column(name = "PAGINATION_URL_REGEX")
	private String paginationUrlRegex;
	
	@Column(name = "LINK_REGEX")
	private String linkRegex;
	
	@Column(name = "PAGINATION_LINK")
	private boolean paginationLink;
	
	@Column(name = "PAGINATION_LINK_REGEX")
	private String paginationLinkRegex;
	
	@Column(name = "SUBLINK_REGEX")
	private String subLinkRegex;
	
	@Column(name = "PAGINATION_SUBLINK")
	private boolean paginationSubLink;
	
	@Column(name = "PAGINATION_SUBLINK_REGEX")
	private String paginationSubLinkRegex;
	
	@Column(name = "DESCRIPTION_REGEX")
	private String descriptionRegex;

	public Integer getIdRule() {
		return idRule;
	}

	public void setIdRule(Integer idRule) {
		this.idRule = idRule;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getUrlRegex() {
		return urlRegex;
	}

	public void setUrlRegex(String urlRegex) {
		this.urlRegex = urlRegex;
	}

	public boolean isPaginationUrl() {
		return paginationUrl;
	}

	public void setPaginationUrl(boolean paginationUrl) {
		this.paginationUrl = paginationUrl;
	}

	public String getPaginationUrlRegex() {
		return paginationUrlRegex;
	}

	public void setPaginationUrlRegex(String paginationUrlRegex) {
		this.paginationUrlRegex = paginationUrlRegex;
	}

	public String getLinkRegex() {
		return linkRegex;
	}

	public void setLinkRegex(String linkRegex) {
		this.linkRegex = linkRegex;
	}

	public boolean isPaginationLink() {
		return paginationLink;
	}

	public void setPaginationLink(boolean paginationLink) {
		this.paginationLink = paginationLink;
	}

	public String getPaginationLinkRegex() {
		return paginationLinkRegex;
	}

	public void setPaginationLinkRegex(String paginationLinkRegex) {
		this.paginationLinkRegex = paginationLinkRegex;
	}

	public String getSubLinkRegex() {
		return subLinkRegex;
	}

	public void setSubLinkRegex(String subLinkRegex) {
		this.subLinkRegex = subLinkRegex;
	}

	public boolean isPaginationSubLink() {
		return paginationSubLink;
	}

	public void setPaginationSubLink(boolean paginationSubLink) {
		this.paginationSubLink = paginationSubLink;
	}

	public String getPaginationSubLinkRegex() {
		return paginationSubLinkRegex;
	}

	public void setPaginationSubLinkRegex(String paginationSubLinkRegex) {
		this.paginationSubLinkRegex = paginationSubLinkRegex;
	}

	public String getDescriptionRegex() {
		return descriptionRegex;
	}

	public void setDescriptionRegex(String descriptionRegex) {
		this.descriptionRegex = descriptionRegex;
	}
}
