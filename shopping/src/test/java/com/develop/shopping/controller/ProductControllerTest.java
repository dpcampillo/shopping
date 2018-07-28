package com.develop.shopping.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.develop.shopping.domain.Product;
import com.develop.shopping.repository.ProductRepository;
import com.develop.shopping.repository.UserRepository;
import com.develop.shopping.security.SecurityCons;
import com.develop.shopping.service.ProductService;
import com.develop.shopping.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private UserService userService;

	@MockBean
	private ProductService productService;
	
	@MockBean
	private ProductRepository productRepository;
	
	@MockBean
	private UserRepository userRepository;

	@Test
	public void givenProducts_whenGetProducts_thenReturnJsonArray() throws Exception {
		Product aux = new Product(5L, "005", "Colgate", 2000.0, "Colgate", 100);
		List<Product> list = Arrays.asList(new Product(2L, "002", "Meat", 3000.0, "Meat", 200), aux);

		BDDMockito.given(productService.findAll()).willReturn(list);

		mvc.perform(get("/api/products").contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + getToken("admin"))).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].code", Matchers.is(aux.getCode())));
	}
	
	public synchronized String getToken(String username) {
		String token = Jwts.builder().setIssuedAt(new Date()).setIssuer(SecurityCons.ISSUER_INFO).setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + SecurityCons.TOKEN_EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SecurityCons.SUPER_SECRET_KEY).compact();
		return token;
	}
	
	

}
