package com.eai.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eai.model.User;
import com.eai.repository.UserRepository;
import com.eai.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        // Load user from the database (throw exception if not found)
        User user = userRepository.findByUserName(username);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Return user object
        return user;
    }

	@Override
	public List<User> findAll() {
		// Load user from the database (throw exception if not found)
        List<User> listUser = userRepository.findAll();
        if(listUser == null || listUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        // Return user object
        return listUser;
	}
}