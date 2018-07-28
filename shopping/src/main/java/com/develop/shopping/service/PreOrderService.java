package com.develop.shopping.service;

import java.util.List;
import java.util.Optional;

import com.develop.shopping.domain.PreOrder;
import com.develop.shopping.exception.StatusException;

public interface PreOrderService {
	
	public PreOrder add(PreOrder preOrder);
	
	public PreOrder update(Long id, PreOrder preOrder) throws StatusException;
	
	public PreOrder delete(Long id) throws StatusException;
	
	public List<PreOrder> deleteByUser(Long idUser);
	
	public Optional<PreOrder> findById(Long id);
	
	public List<PreOrder> findAllByUser(Long idUser);
	
	public List<PreOrder> findAll();
	
}
