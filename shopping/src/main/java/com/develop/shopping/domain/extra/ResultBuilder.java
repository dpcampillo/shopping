package com.develop.shopping.domain.extra;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
/**
 * Utilidad para crear una respuesta json a base de claves y valores
 * @author Usuario
 *
 */
public class ResultBuilder {

	private Map<String, Object> playload = new HashMap<>();

	public ResultBuilder(String key, String value) {
		playload.put(key, value);
	}

	public ResultBuilder put(String key, Object value) {
		this.playload.put(key, value);
		return this;
	}
	
	/**
	 * Convertir el mapa de claves y valores a Json mediante el objeto Gson
	 * @return
	 */
	public String getPlayload() {
		try {
			return new Gson().toJson(playload);
		} catch (Exception e) {
			return "";
		}
	}

}
