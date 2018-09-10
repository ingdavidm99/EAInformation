package com.eai.service;

import com.eai.model.UserDetail;

public interface UserDetailService{

	public UserDetail findByUserDetail(Integer idUserDetail);
	
	public UserDetail saveUserDetail(UserDetail userDetail);
	
	public UserDetail updateUserDetail(UserDetail userDetail);
	
	public UserDetail updateUserPassword(UserDetail userDetail);
	
}