package com.eai.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "system_parameter")
public class SystemParameter implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SYSTEM_PARAMETER", nullable = false)
    private Integer idSystemParameter;
    
    @Column(name = "NAME", nullable = false)
    private String name;
    
    @Lob
    @Column(name = "VALUE", nullable = false)
    private String value;
    
    @Lob
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;
    
    @Column(name = "TYPE", nullable = false)
    private Integer type;
    
    @Column(name = "USER_NAME", nullable = false)
    private String userName;
    
    @Column(name = "DATE", nullable = false)
    private String date;
    
    public SystemParameter() {
	}
    
    public SystemParameter(String name, String value, String description, String userName, String date) {
    	this.name = name;
    	this.value = value;
		this.description = description;
		this.userName = userName;
		this.date = date;
	}

	public Integer getIdSystemParameter() {
		return idSystemParameter;
	}

	public void setIdSystemParameter(Integer idSystemParameter) {
		this.idSystemParameter = idSystemParameter;
	}	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}	
}