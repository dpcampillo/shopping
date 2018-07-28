package com.develop.shopping.exception;

import org.springframework.http.HttpStatus;

/**
 * Excepcion para tratar los estados cuando pase algo inseperado en los servicios
 * @author Usuario
 *
 */
public class StatusException extends Exception{

	private static final long serialVersionUID = 1L;
	
	private HttpStatus status;
	
	public StatusException(String msg, HttpStatus status){
		super(msg);
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}
	
}
