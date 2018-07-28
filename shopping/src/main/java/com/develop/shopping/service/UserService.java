package com.develop.shopping.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.develop.shopping.domain.User;

public interface UserService extends UserDetailsService {
	public User findByUsername(String username);
}
