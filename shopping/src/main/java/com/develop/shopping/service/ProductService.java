package com.develop.shopping.service;

import java.util.List;
import java.util.Optional;

import com.develop.shopping.domain.Product;
import com.develop.shopping.exception.StatusException;

public interface ProductService {
	public List<Product> findAll();
	public Optional<Product> findById(Long id);
	public Product update(Long id, Product product) throws StatusException;
}
