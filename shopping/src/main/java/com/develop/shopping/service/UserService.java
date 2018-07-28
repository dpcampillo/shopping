package com.develop.shopping.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.develop.shopping.domain.User;

/**
 * Servicio de Usuario
 * @author Usuario
 *
 */
public interface UserService extends UserDetailsService {
	/**
	 * Buscar usuario por username
	 * @param username
	 * @return
	 */
	public User findByUsername(String username);
}
