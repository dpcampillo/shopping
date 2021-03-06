package com.develop.shopping.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.develop.shopping.domain.Product;
import com.develop.shopping.service.ProductService;

/**
 * Api para los productosw2
 * @author Usuario
 *
 */
@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	private ProductService productService;

	public static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public ResponseEntity<?> getAllProducts() {
		List<Product> list = productService.findAll();
		logger.info("Récupération du catalogue de produits");

		if (list.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<Product> getDetailById(@PathVariable("id") Long idProduct) {
		Optional<Product> product = productService.findById(idProduct);
		logger.info("Détail du produit {}", idProduct);

		if (!product.isPresent()) {
			return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Product>(product.get(), HttpStatus.OK);
	}
}
