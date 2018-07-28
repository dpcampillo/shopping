package com.develop.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.develop.shopping.domain.User;

/**
 * Repositorio de los Usuarios
 * @author Usuario
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	public User findByUsername(String username);
}
