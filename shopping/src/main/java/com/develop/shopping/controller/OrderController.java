package com.develop.shopping.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.develop.shopping.domain.Order;
import com.develop.shopping.domain.User;
import com.develop.shopping.exception.StatusException;
import com.develop.shopping.service.OrderService;
import com.develop.shopping.service.UserService;

/**
 * Api para las ordenes
 * @author Usuario
 *
 */
@RestController
@RequestMapping("/api")
public class OrderController {
	@Autowired
	private UserService userService;
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value = "/orders", method = RequestMethod.GET)
	public ResponseEntity<?> getPreOrdersByUser(Principal principal) {
		User user = userService.findByUsername(principal.getName());
		List<Order> list = orderService.findAllByUser(user.getId());
		if (list.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "/orders", method = RequestMethod.POST)
	public ResponseEntity<?> actionOrder(Principal principal) {
		try {
			User user = userService.findByUsername(principal.getName());
			return new ResponseEntity<>(orderService.actionOrder(user.getId()), HttpStatus.OK);
		} catch (StatusException e) {
			return new ResponseEntity<>(e.getMessage(), e.getStatus());
		}
	}

}
