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
@Table(name = "level_four")
public class LevelFour implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_LEVEL_4")
    private Integer idLevel4;
    
    @Column(name = "ALPHABET")
    private String alphabet;
    
    @Column(name = "CATEGORY")
    private String category;
    
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "PRICE_USD")
    private String priceUsd;

    @Column(name = "PRICE_COP")
    private String priceCop;

    @Lob
    @Column(name = "ATTACHMENT")
    private String attachment;
    
    @Column(name = "ID_LEVEL_3")
    private Integer idLevel3;

	public LevelFour(String alphabet, String category, String name, String priceUsd, String priceCop, String attachment, Integer idLevel3) {
		this.alphabet = alphabet;
		this.category = category;
		this.name = name;
		this.priceUsd = priceUsd;
		this.priceCop = priceCop;
		this.attachment = attachment;
		this.idLevel3 = idLevel3;
	}

	public Integer getIdLevel4() {
		return idLevel4;
	}

	public void setIdLevel4(Integer idLevel4) {
		this.idLevel4 = idLevel4;
	}
}
