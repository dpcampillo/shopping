package com.develop.shopping.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
/**
 * Entidad usuario para verificar la autenticacion
 * @author Usuario
 *
 */
@Entity(name="user")
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private Long id;
	private String username;
	private String password;
	private String fullname;
	
	public User(){
		
	}
	
	public User(Long id){
		this.id = id;
	}
	
	public User(String username){
		this.username = username;
	}
	
	public User(Long id, String username, String password, String fullname) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.fullname = fullname;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	
	
}
