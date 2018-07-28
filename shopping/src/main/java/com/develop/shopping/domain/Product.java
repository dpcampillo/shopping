package com.develop.shopping.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="product")
public class Product implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private Long id;
	private String code;
	private String name;
	private Double price;	
	private String description;
	@Column(name="qty_stock")
	private Integer qtyStock;
	
	public Product() {
		
	}
	
	public Product(Long id, String code, String name, Double price, String description, Integer qtyStock) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.price = price;
		this.description = description;
		this.qtyStock = qtyStock;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getQtyStock() {
		return qtyStock;
	}

	public void setQtyStock(Integer qtyStock) {
		this.qtyStock = qtyStock;
	}	
}
