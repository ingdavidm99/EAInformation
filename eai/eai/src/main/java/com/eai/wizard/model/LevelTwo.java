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
@Table(name = "level_two")
public class LevelTwo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_LEVEL_2")
    private Integer idLevel2;
    
    @Column(name = "CATEGORY")
    private String category;
    
    @Column(name = "AMOUNT")
    private int amount;
    
    @Lob
    @Column(name = "URL")
    private String url;
    
    @Column(name = "STATUS")
    private String status;
    
    @Column(name = "ID_LEVEL_1")
    private Integer idLevel1;

    public LevelTwo() {}

    public LevelTwo(Integer idLevel2) {
        this.idLevel2 = idLevel2;
    }

    public LevelTwo(String category, int amount, String url, String status, Integer idLevel1) {
    	this.category = category;
        this.amount = amount;
        this.url = url;
        this.status = status;
        this.idLevel1 = idLevel1;
    }

    public Integer getIdLevel2() {
        return idLevel2;
    }

    public void setIdLevel2(Integer idLevel2) {
        this.idLevel2 = idLevel2;
    }
    
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIdLevel1() {
        return idLevel1;
    }

    public void setIdLevel1(Integer idLevel1) {
        this.idLevel1 = idLevel1;
    }	
}
