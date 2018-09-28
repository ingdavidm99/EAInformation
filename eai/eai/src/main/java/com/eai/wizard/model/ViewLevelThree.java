package com.eai.wizard.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "view_level_three")
public class ViewLevelThree implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_LEVEL_3")
    private Integer idLevel3;
    
    @Column(name = "URL")
    private String url;
    
    @Column(name = "STATUS")
    private String status;
    
    public Integer getIdLevel3() {
        return idLevel3;
    }

    public void setIdLevel3(Integer idLevel3) {
        this.idLevel3 = idLevel3;
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
