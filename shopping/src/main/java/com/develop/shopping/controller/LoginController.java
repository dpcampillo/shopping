package com.develop.shopping.controller;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.develop.shopping.domain.User;
import com.develop.shopping.domain.extra.ResultBuilder;
import com.develop.shopping.domain.extra.UserApp;
import com.develop.shopping.security.SecurityCons;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Controlador que devuelve el acceso de un usuario a partir de un username y un password
 * @author Usuario
 *
 */
@RestController
public class LoginController {

	private AuthenticationManager authenticationManager;

	public LoginController(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@RequestMapping(value = "/login", method = { RequestMethod.POST })
	public ResponseEntity<?> login(@RequestBody User user) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), new ArrayList<>()));
			Object principal = authentication.getPrincipal();
			if (principal instanceof UserApp) {
				String token = getToken(((UserApp) principal).getUsername());
				HttpHeaders headers = new HttpHeaders();
				headers.add("X-Token", token);
				return new ResponseEntity<>(
						new ResultBuilder("X-Token", token)
								.put("X-Info", ((UserApp) principal).getInformacion().toString()).getPlayload(),
						headers, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new ResultBuilder("X-Error", "No se pudo completar la autenticacion"),
						HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(new ResultBuilder("X-Error", e.getMessage()).getPlayload(),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Generacion del Token Valido
	 * @param username
	 * @return
	 */
	public synchronized String getToken(String username) {
		String token = Jwts.builder().setIssuedAt(new Date()).setIssuer(SecurityCons.ISSUER_INFO).setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + SecurityCons.TOKEN_EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SecurityCons.SUPER_SECRET_KEY).compact();
		return token;
	}

}
