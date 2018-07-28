package com.develop.shopping.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.develop.shopping.domain.PreOrder;
import com.develop.shopping.domain.User;
import com.develop.shopping.exception.StatusException;
import com.develop.shopping.service.PreOrderService;
import com.develop.shopping.service.UserService;

@RestController
@RequestMapping("/api")
public class PreOrderController {

	public static final Logger logger = LoggerFactory.getLogger(PreOrderController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private PreOrderService preOrderService;

	@RequestMapping(value = "/preorders", method = RequestMethod.GET)
	public ResponseEntity<?> getPreOrdersByUser(Principal principal) {
		User user = userService.findByUsername(principal.getName());
		List<PreOrder> list = preOrderService.findAllByUser(user.getId());
		if (list.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "/preorders/{id}", method = RequestMethod.PUT)
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<?> getPreOrderById(@PathVariable("id") Long idPreOrder, @RequestBody PreOrder preOrder) {
		try {
			return new ResponseEntity<>(preOrderService.update(idPreOrder, preOrder), HttpStatus.OK);
		} catch (StatusException e) {
			return new ResponseEntity<>(e.getMessage(), e.getStatus());
		}
	}

	@RequestMapping(value = "/preorders", method = RequestMethod.POST)
	public ResponseEntity<?> addPreOrder(@RequestBody PreOrder preOrder, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		preOrder.setIdUser(user.getId());
		preOrderService.add(preOrder);
		return new ResponseEntity<>(preOrderService.findAllByUser(preOrder.getIdUser()), HttpStatus.OK);
	}

	@RequestMapping(value = "/preorders/{id}", method = RequestMethod.DELETE)
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<?> deletePreOrderById(@PathVariable("id") Long idPreOrder) {
		try {
			return new ResponseEntity<>(preOrderService.delete(idPreOrder), HttpStatus.OK);
		} catch (StatusException e) {
			return new ResponseEntity<>(e.getMessage(), e.getStatus());
		}
	}

}
