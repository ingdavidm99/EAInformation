package com.eai.dto;

import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;
import java.util.function.Predicate;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.csrf.CsrfToken;

import com.eai.model.ParentMenu;
import com.eai.service.MenuService;
import com.eai.service.ParentMenuService;
import com.eai.service.SystemParametersService;
import com.eai.service.UserService;

public class TransactionPage implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String userName;
	
	private String title;
	
	private Integer idRole;
	
	private List<ParentMenu> parentMenuList;
	
	private String token;
	
	private String headerName;
	
	private Long pageSize;
	
	private String local;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getIdRole() {
		return idRole;
	}

	public void setIdRole(Integer idRole) {
		this.idRole = idRole;
	}
	
	public List<ParentMenu> getParentMenuList() {
		return parentMenuList;
	}

	public void setParentMenuList(List<ParentMenu> parentMenuList) {
		this.parentMenuList = parentMenuList;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getHeaderName() {
		return headerName;
	}

	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}

	public Long getPageSize() {
		return pageSize;
	}

	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}
	
	public TransactionPage(String locale) {
		this.local = locale;
	}
	
	public static TransactionPage getData(
			HttpServletRequest request, 
			UserService userService, 
			ParentMenuService parentMenuService,
			MenuService menuService,
			SystemParametersService systemParametersService) {
		
		Object user = request.getSession().getAttribute("user");
		
		TransactionPage transactionPage =  new TransactionPage(systemParametersService.findById(7).getValue());
		
		if(user != null) {
			CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
			
			Integer role = userService.findByUserName(user.toString()).getRole().getIdRole();
			
			List<ParentMenu> pmist = parentMenuService.parentMenuList(role);
			for (ParentMenu parentMenu : pmist) {
				parentMenu.setMenuList(menuService.menuList(parentMenu.getIdParentMenu(), role));
			}
			
			Predicate<ParentMenu> parentMenuListPredicate = p-> p.getMenuList().isEmpty();
			pmist.removeIf(parentMenuListPredicate);
			
			transactionPage.setUserName(user.toString());
			transactionPage.setIdRole(role);
			transactionPage.setParentMenuList(pmist);
			transactionPage.setHeaderName(csrfToken.getHeaderName());
			transactionPage.setToken(csrfToken.getToken());
			
			transactionPage.setPageSize(new Long(systemParametersService.findById(1).getValue()));
			transactionPage.setTitle(transactionPage.get("/index"));
		}
		
		return transactionPage;
	}
	
	public static TransactionPage getData(HttpServletRequest request, String key) {
		TransactionPage transactionPage = (TransactionPage) request.getSession().getAttribute(Constants.TRANSACTIONPAGE.val());
		transactionPage.setTitle(transactionPage.get(key));
		
		return transactionPage;
	}
	
	public static String getData(HttpServletRequest request) {
		TransactionPage transactionPage = (TransactionPage) request.getSession().getAttribute(Constants.TRANSACTIONPAGE.val());
		
		return transactionPage.getLocal();
	}
	
	public String get(String key) {
		Properties props = new Properties();
		
		try{
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			
			if("ES".equals(local)) {
				props.load(new InputStreamReader(loader.getResourceAsStream("messages_es.properties"), "UTF-8"));
			}else {
				props.load(new InputStreamReader(loader.getResourceAsStream("messages_en.properties"), "UTF-8"));
			}
		}catch (Exception e) {
			return key;
		}
				
		return  props.getProperty(key);
	}
	
}
