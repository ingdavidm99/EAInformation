package com.eai.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eai.model.Role;
import com.eai.model.User;
import com.eai.model.UserDetail;
import com.eai.repository.UserDetailRepository;
import com.eai.repository.UserRepository;
import com.eai.service.UserDetailService;

@Service
public class UserDetailServiceImpl implements UserDetailService {
    @Autowired
    private UserDetailRepository userDetailRepository;
    
    @Autowired
    private UserRepository userRepository;
    

	@Override
	@Transactional
	public UserDetail findByUserDetail(Integer idUserDetail) {
		return userDetailRepository.findById(idUserDetail).orElse(null);
	}

    @Override
    @Transactional
    public UserDetail saveUserDetail(UserDetail userDetail){
        
    	userDetail = userDetailRepository.save(userDetail);
        if(userDetail != null) {
        	User user = new User();
        	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
			String hashedPassword = passwordEncoder.encode(userDetail.getPassword());
        	
        	user.setUserDetail(userDetail);
        	user.setUserName(userDetail.getUserName());
        	user.setPassword(hashedPassword);
        	user.setEnabled(true);
        	user.setRole(new Role(new Integer("2")));
        	userRepository.save(user);
        }

        return userDetail;
    }
    
    @Override
    @Transactional
    public UserDetail updateUserDetail(UserDetail userDetail){
        return userDetailRepository.save(userDetail);
    }

	@Override
	@Transactional
	public UserDetail updateUserPassword(UserDetail userDetail) {
		User user = userRepository.findByUserName(userDetail.getUserName());
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
		String hashedPassword = passwordEncoder.encode(userDetail.getNewPassword());
		user.setPassword(hashedPassword);
		
		userRepository.save(user);
		
		return userDetail;
	}
}