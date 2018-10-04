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
@Table(name = "level_one")
public class LevelOne implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_LEVEL_1")
    private Integer idLevel1;
    
    @Column(name = "ALPHABET")
    private String alphabet;
    
    @Lob
    @Column(name = "URL")
    private String url;
    
    @Column(name = "STATUS")
    private String status;
    
    public LevelOne() {}

    public LevelOne(Integer idLevel1) {
        this.idLevel1 = idLevel1;
    }
    
    public LevelOne(String alphabet) {
        this.alphabet = alphabet;
    }

    public LevelOne(String alphabet, String url, String status) {
        this.alphabet = alphabet;
        this.url = url;
        this.status = status;
    }

    public Integer getIdLevel1() {
        return idLevel1;
    }

    public void setIdLevel1(Integer idLevel1) {
        this.idLevel1 = idLevel1;
    }

    public String getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet;
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
}