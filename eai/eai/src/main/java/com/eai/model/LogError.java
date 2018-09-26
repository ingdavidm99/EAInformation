package com.eai.model;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "log_error")
public class LogError implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_LOG_ERROR", nullable = false)
    private Integer idLogError;
    
    @Lob
    @Column(name = "ERROR", nullable = false)
    private String error;
    
    @Column(name = "USER_NAME", nullable = false)
    private String userName;
    
    @Column(name = "PATH", nullable = false)
    private String path;
    
    @Column(name = "DATE", nullable = false)
    private String date;
     
    public LogError() {
	}
    
    public LogError(Exception exception,String userName, String path) {
    	this.userName = (userName == null)? "N/A" : userName;
		this.path = path;
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		this.date = dt.format(new Date());
		this.error = getStackTrace(exception);
	}
    
    public String getStackTrace(Exception exception) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		exception.printStackTrace(printWriter);
		
		return result.toString();
	}

	public Integer getIdLogError() {
		return idLogError;
	}

	public void setIdLogError(Integer idLogError) {
		this.idLogError = idLogError;
	}
	
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
		
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}	
}