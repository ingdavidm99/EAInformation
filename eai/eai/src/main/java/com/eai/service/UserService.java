package com.eai.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.eai.model.User;

public interface UserService extends UserDetailsService {
    
	User findByUserName(String userName);
    
    List<User> findAll();
}