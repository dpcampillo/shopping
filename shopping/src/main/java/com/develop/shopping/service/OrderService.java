package com.develop.shopping.service;

import java.util.List;
import java.util.Optional;

import com.develop.shopping.domain.Order;
import com.develop.shopping.exception.StatusException;

/**
 * Servicio de Ordenes
 * @author Usuario
 *
 */
public interface OrderService {
	
	/**
	 * Metodo para generar una orden a partir de las diferentes preordenes que tenga el usuario agregada
	 * Si no existen preordenes el sistema no realizara el registro de una nueva orden 
	 * @param idUser
	 * @return
	 * @throws StatusException
	 */
	public Order actionOrder(Long idUser) throws StatusException;
	
	/**
	 * Agregar una orden
	 * @param order
	 * @return
	 */
	public Order add(Order order);
	
	/**
	 * Actualizar una orden
	 * @param id
	 * @param order
	 * @return
	 * @throws StatusException
	 */
	public Order update(Long id, Order order) throws StatusException;
	
	/**
	 * eliminar una orden
	 * @param id
	 * @return Orden eliminada
	 * @throws StatusException
	 */
	public Order delete(Long id) throws StatusException;
	
	/**
	 * Buscar Orden por Id
	 * @param id
	 * @return
	 */
	public Optional<Order> findById(Long id);
	
	/**
	 * Buscar Ordenes por usuario
	 * @param idUser
	 * @return
	 */
	public List<Order> findAllByUser(Long idUser);
	
	/**
	 * Buscar todas las ordenes
	 * @return
	 */
	public List<Order> findAll();
	
}
