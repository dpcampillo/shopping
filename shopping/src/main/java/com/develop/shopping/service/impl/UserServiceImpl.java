package com.develop.shopping.service.impl;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.develop.shopping.domain.User;
import com.develop.shopping.domain.extra.UserApp;
import com.develop.shopping.repository.UserRepository;
import com.develop.shopping.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		return new UserApp(user.getUsername(), user.getPassword(),user.getFullname(), Collections.emptyList());
	}
	
	@Override
	@Transactional(readOnly = true)
	public User findByUsername(String username){
		return userRepository.findByUsername(username);
	}

}
