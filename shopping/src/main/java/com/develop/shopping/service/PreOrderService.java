package com.develop.shopping.service;

import java.util.List;
import java.util.Optional;

import com.develop.shopping.domain.PreOrder;
import com.develop.shopping.exception.StatusException;

/**
 * Servicio de Preordenes
 * @author Usuario
 *
 */
public interface PreOrderService {
	
	/**
	 * Agregar una preOrden
	 * @param preOrder
	 * @return
	 */
	public PreOrder add(PreOrder preOrder);
	
	/**
	 * Actualizar una Preorden
	 * @param id
	 * @param preOrder
	 * @return
	 * @throws StatusException
	 */
	public PreOrder update(Long id, PreOrder preOrder) throws StatusException;
	
	/**
	 * Eliminar una preorden
	 * @param id
	 * @return Preorden eliminada
	 * @throws StatusException
	 */
	public PreOrder delete(Long id) throws StatusException;
	
	/**
	 * Eliminar Preordenes por usuario
	 * @param idUser
	 * @return Listado de preordenes eliminadas
	 */
	public List<PreOrder> deleteByUser(Long idUser);
	
	/**
	 * Buscar Preordenes por Id
	 * @param id
	 * @return
	 */
	public Optional<PreOrder> findById(Long id);
	
	/**
	 * Buscar Preordenes por usuario
	 * @param idUser
	 * @return
	 */
	public List<PreOrder> findAllByUser(Long idUser);
	
	/**
	 * Buscar todas las Preordenes
	 * @return
	 */
	public List<PreOrder> findAll();
	
}
