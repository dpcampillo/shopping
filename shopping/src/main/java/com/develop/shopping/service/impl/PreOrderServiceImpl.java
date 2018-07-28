package com.develop.shopping.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.develop.shopping.domain.PreOrder;
import com.develop.shopping.exception.StatusException;
import com.develop.shopping.repository.PreOrderRepository;
import com.develop.shopping.service.PreOrderService;

@Service
public class PreOrderServiceImpl implements PreOrderService {

	@Autowired
	private PreOrderRepository preOrderRepository;

	@Override
	public Optional<PreOrder> findById(Long id) {
		return preOrderRepository.findById(id);
	}

	@Override
	public PreOrder add(PreOrder preOrder) {
		List<PreOrder> list = findPreOrderByProduct(preOrder.getIdUser(), preOrder.getIdProduct());
		if (!list.isEmpty()) {
			PreOrder preOrderAux = list.get(0);
			preOrderAux.setQuantity(preOrderAux.getQuantity() + preOrder.getQuantity());
			preOrderRepository.flush();
			return preOrderAux;
		} else {
			return preOrderRepository.save(preOrder);
		}
	}

	@Override
	public PreOrder update(Long id, PreOrder preOrder) throws StatusException {
		Optional<PreOrder> preOrderAux = findById(id);
		if (!preOrderAux.isPresent()) {
			throw new StatusException("Not found " + id, HttpStatus.NOT_FOUND);
		}
		preOrderAux.get().setQuantity(preOrder.getQuantity());
		preOrderRepository.flush();
		return preOrderAux.get();
	}

	@Override
	public List<PreOrder> findAllByUser(Long idUser) {
		return preOrderRepository.findAll(new Specification<PreOrder>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<PreOrder> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				predicates.add(criteriaBuilder.equal(root.get("idUser"), idUser));
				return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			}
		});
	}

	@Override
	public List<PreOrder> findAll() {
		return preOrderRepository.findAll();
	}

	private List<PreOrder> findPreOrderByProduct(Long idUser, Long idProduct) {
		return preOrderRepository.findAll(new Specification<PreOrder>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<PreOrder> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				predicates.add(criteriaBuilder.equal(root.get("idUser"), idUser));
				predicates.add(criteriaBuilder.equal(root.get("idProduct"), idProduct));
				return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			}
		});
	}

	@Override
	public PreOrder delete(Long id) throws StatusException {
		Optional<PreOrder> preOrderAux = findById(id);
		if (!preOrderAux.isPresent()) {
			throw new StatusException("Not found " + id, HttpStatus.NOT_FOUND);
		}
		preOrderRepository.delete(preOrderAux.get());
		preOrderRepository.flush();
		return preOrderAux.get();
	}

	@Override
	@Transactional
	public List<PreOrder> deleteByUser(Long idUser) {
		List<PreOrder> listPreOrders = findAllByUser(idUser);
		listPreOrders.forEach(action->{
			preOrderRepository.delete(action);
		});
		preOrderRepository.flush();
		return listPreOrders;
	}

}
