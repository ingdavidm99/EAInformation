package com.eai.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.eai.dto.Pagination;
import com.eai.model.User;

public interface UserService extends UserDetailsService {
	
	public User findById(Integer idUser);
    
	public User findByUserName(String userName);
    
	public void findAll(Pagination pagination, Long pageSize);
	
	public User saveOrUpdate(User userOld);
}