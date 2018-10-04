package com.eai.wizard.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "level_three")
public class LevelThree implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_LEVEL_3")
    private Integer idLevel3;
    
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "PRICE")
    private String price;
    
    @Lob
    @Column(name = "ATTACHMENT")
    private String attachment;
    
    @Column(name = "STATUS")
    private String status;
    
    @Lob
    @Column(name = "URL")
    private String url;
    
    @Column(name = "ID_LEVEL_2")
    private Integer idLevel2;
    
    public LevelThree(String name, String price, String attachment, String url, String status, Integer idLevel2) {
    	this.name = name;
    	this.price = price;
    	this.attachment = attachment;
    	this.url = url;
    	this.status = status;
        this.idLevel2 = idLevel2;
    }

    public Integer getIdLevel3() {
        return idLevel3;
    }

    public void setIdLevel3(Integer idLevel3) {
        this.idLevel3 = idLevel3;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
