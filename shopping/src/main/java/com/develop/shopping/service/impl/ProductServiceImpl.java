package com.develop.shopping.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.develop.shopping.domain.Product;
import com.develop.shopping.exception.StatusException;
import com.develop.shopping.repository.ProductRepository;
import com.develop.shopping.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;

	public static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Override
	@Transactional(readOnly = true)
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Product> findById(Long id) {
		return productRepository.findById(id);
	}

	@Override
	public Product update(Long id, Product product) throws StatusException {
		Optional<Product> productAux = findById(id);
		if (!productAux.isPresent()) {
			throw new StatusException("Not found " + id, HttpStatus.NOT_FOUND);
		}
		productAux.get().setQtyStock(product.getQtyStock());
		productAux.get().setCode(product.getCode());
		productAux.get().setDescription(product.getDescription());
		productAux.get().setName(product.getName());
		productAux.get().setPrice(product.getPrice());
		productRepository.flush();
		return productAux.get();
	}
}
