package com.develop.shopping.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.develop.shopping.domain.PreOrder;
import com.develop.shopping.domain.Product;
import com.develop.shopping.domain.User;
import com.develop.shopping.repository.PreOrderRepository;
import com.develop.shopping.repository.ProductRepository;
import com.develop.shopping.repository.UserRepository;
import com.develop.shopping.security.SecurityCons;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Prueba de integracion de las ordenes
 * @author Usuario
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class OrderControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private PreOrderRepository preOrderRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder byBCryptPasswordEncoder;

	private void createPreOrder() {
		userRepository.save(new User(1L, "admin", byBCryptPasswordEncoder.encode("123456"), "Dario Perez"));
		productRepository.save(new Product(4L, "004", "Chair", 500.0, "Chair", 50));
		productRepository.save(new Product(5L, "005", "Colgate", 2000.0, "Colgate", 100));

		PreOrder preOrder = new PreOrder();
		preOrder.setIdProduct(4L);
		preOrder.setIdUser(1L);
		preOrder.setQuantity(3);
		preOrderRepository.save(preOrder);

		preOrder = new PreOrder();
		preOrder.setIdProduct(5L);
		preOrder.setIdUser(1L);
		preOrder.setQuantity(2);
		preOrderRepository.save(preOrder);
	}

	public synchronized String getToken(String username) {
		String token = Jwts.builder().setIssuedAt(new Date()).setIssuer(SecurityCons.ISSUER_INFO).setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + SecurityCons.TOKEN_EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SecurityCons.SUPER_SECRET_KEY).compact();
		return token;
	}

	@Test
	public void givenOrdersWhenSendActionOrderThenStatus200() throws Exception {
		createPreOrder();
		mvc.perform(MockMvcRequestBuilders.post("/api/orders").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + getToken("admin"))).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.total", Matchers.is((3 * 500.0) + (2 * 2000.0))));
	}

}
