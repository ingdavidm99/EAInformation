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

	public LevelFour() {
	}
	
	public LevelFour(Integer idLevel4) {
		this.idLevel4 = idLevel4;
	}

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

	public String getAlphabet() {
		return alphabet;
	}

	public void setAlphabet(String alphabet) {
		this.alphabet = alphabet;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPriceUsd() {
		return priceUsd;
	}

	public void setPriceUsd(String priceUsd) {
		this.priceUsd = priceUsd;
	}

	public String getPriceCop() {
		return priceCop;
	}

	public void setPriceCop(String priceCop) {
		this.priceCop = priceCop;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public Integer getIdLevel3() {
		return idLevel3;
	}

	public void setIdLevel3(Integer idLevel3) {
		this.idLevel3 = idLevel3;
	}	
}
