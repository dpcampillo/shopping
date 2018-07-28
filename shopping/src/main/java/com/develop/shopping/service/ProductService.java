package com.develop.shopping.service;

import java.util.List;
import java.util.Optional;

import com.develop.shopping.domain.Product;
import com.develop.shopping.exception.StatusException;

/**
 * Servicio de Productos
 * @author Usuario
 *
 */
public interface ProductService {
	/**
	 * Buscar todos los productos
	 * @return
	 */
	public List<Product> findAll();
	/**
	 * Buscar Producto por Id
	 * @param id
	 * @return
	 */
	public Optional<Product> findById(Long id);
	/**
	 * Actualizar Producto
	 * @param id
	 * @param product
	 * @return
	 * @throws StatusException
	 */
	public Product update(Long id, Product product) throws StatusException;
}
