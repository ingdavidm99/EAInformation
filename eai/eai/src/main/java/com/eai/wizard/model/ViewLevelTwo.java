package com.eai.wizard.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "view_level_two")
public class ViewLevelTwo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_LEVEL_2")
    private Integer idLevel2;
    
    @Column(name = "URL")
    private String url;
    
    @Column(name = "STATUS")
    private String status;
    
    public Integer getIdLevel2() {
        return idLevel2;
    }

    public void setIdLevel2(Integer idLevel2) {
        this.idLevel2 = idLevel2;
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
