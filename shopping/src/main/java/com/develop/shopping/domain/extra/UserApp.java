package com.develop.shopping.domain.extra;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * Utilidad para autenticar los usuarios en los filtros de seguridad de spring
 * @author Usuario
 *
 */
public class UserApp extends User{

	private static final long serialVersionUID = 1L;
	
	private Object informacion;
	
	public UserApp(String username, String password,Object informacion, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.informacion = informacion;
	}
	
	public Object getInformacion() {
		return informacion;
	}

}
