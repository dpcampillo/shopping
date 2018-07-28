package com.develop.shopping.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.develop.shopping.domain.Order;
import com.develop.shopping.domain.OrderDetail;
import com.develop.shopping.domain.PreOrder;
import com.develop.shopping.domain.Product;
import com.develop.shopping.exception.StatusException;
import com.develop.shopping.repository.OrderRepository;
import com.develop.shopping.service.OrderService;
import com.develop.shopping.service.PreOrderService;
import com.develop.shopping.service.ProductService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private PreOrderService preOrderService;
	@Autowired
	private ProductService productService;

	@Override
	public Optional<Order> findById(Long id) {
		return orderRepository.findById(id);
	}

	@Override
	public Order add(Order order) {
		return orderRepository.save(order);
	}

	@Override
	public Order update(Long id, Order order) throws StatusException {
		Optional<Order> orderAux = findById(id);
		if (!orderAux.isPresent()) {
			throw new StatusException("Not found " + id, HttpStatus.NOT_FOUND);
		}
		orderAux.get().setCode(order.getCode());
		orderAux.get().setDate(order.getDate());
		orderAux.get().setTotal(order.getTotal());
		orderRepository.flush();
		return orderAux.get();
	}

	@Override
	public List<Order> findAllByUser(Long idUser) {
		return orderRepository.findAll(new Specification<Order>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				predicates.add(criteriaBuilder.equal(root.get("idUser"), idUser));
				return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			}
		});
	}

	@Override
	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	@Override
	public Order delete(Long id) throws StatusException {
		Optional<Order> orderAux = findById(id);
		if (!orderAux.isPresent()) {
			throw new StatusException("Not found " + id, HttpStatus.NOT_FOUND);
		}
		orderRepository.delete(orderAux.get());
		orderRepository.flush();
		return orderAux.get();
	}

	@Override
	@Transactional
	public Order actionOrder(Long idUser) throws StatusException {
		List<PreOrder> listPreOrders = preOrderService.findAllByUser(idUser);
		Double total = 0.0;
		Order order = new Order();
		order.setCode(UUID.randomUUID().toString());
		order.setDate(new Timestamp(new Date().getTime()));
		order.setIdUser(idUser);

		if (listPreOrders.isEmpty()) {
			throw new StatusException("No details for order", HttpStatus.NO_CONTENT);
		}

		for (PreOrder preOrder : listPreOrders) {
			Optional<Product> product = productService.findById(preOrder.getIdProduct());
			if (!product.isPresent()) {
				throw new StatusException("Product with Id" + preOrder.getIdProduct() + " Not Found",
						HttpStatus.NOT_FOUND);
			}

			if (preOrder.getQuantity() > product.get().getQtyStock()) {
				throw new StatusException("Product with Id" + preOrder.getIdProduct() + " Not Stock Enought",
						HttpStatus.BAD_REQUEST);
			}

			Double subtotal = (product.get().getPrice() * preOrder.getQuantity());
			total += subtotal;

			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setIdProduct(product.get().getId());
			orderDetail.setQuantity(preOrder.getQuantity());
			orderDetail.setSubtotal(subtotal);
			orderDetail.setOrder(order);
			order.getDatails().add(orderDetail);
			product.get().setQtyStock(product.get().getQtyStock() - preOrder.getQuantity());
			productService.update(product.get().getId(), product.get());
		}
		order.setTotal(total);
		preOrderService.deleteByUser(idUser);
		return orderRepository.save(order);
	}

}
