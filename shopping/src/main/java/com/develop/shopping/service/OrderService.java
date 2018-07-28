package com.develop.shopping.service;

import java.util.List;
import java.util.Optional;

import com.develop.shopping.domain.Order;
import com.develop.shopping.exception.StatusException;

public interface OrderService {
	
	public Order actionOrder(Long idUser) throws StatusException;
	
	public Order add(Order order);
	
	public Order update(Long id, Order order) throws StatusException;
	
	public Order delete(Long id) throws StatusException;
	
	public Optional<Order> findById(Long id);
	
	public List<Order> findAllByUser(Long idUser);
	
	public List<Order> findAll();
	
}
