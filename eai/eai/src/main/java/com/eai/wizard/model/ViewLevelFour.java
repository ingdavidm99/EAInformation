package com.eai.wizard.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "view_level_four")
public class ViewLevelFour implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_LEVEL_3")
    private Integer idLevel3;
    
    @Column(name = "ALPHABET")
    private String alphabet;
    
    @Column(name = "CATEGORY")
    private String category;
    
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "PRICE")
    private String price;
    
    @Column(name = "ATTACHMENT")
    private String attachment;

	public Integer getIdLevel3() {
		return idLevel3;
	}

	public void setIdLevel3(Integer idLevel3) {
		this.idLevel3 = idLevel3;
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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
}
