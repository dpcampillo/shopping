package com.develop.shopping.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.develop.shopping.domain.Product;
import com.develop.shopping.repository.ProductRepository;
import com.develop.shopping.service.impl.ProductServiceImpl;

@RunWith(SpringRunner.class)
public class ProductServiceImplTest {

	@TestConfiguration
	static class ProductServiceImplTestContextConfiguration {
		@Bean
		public ProductService productService() {
			return new ProductServiceImpl();
		}
	}

	@Autowired
	private ProductService productService;

	@MockBean
	private ProductRepository productRepository;

	@Before
	public void setUp() {
		Mockito.when(productRepository.findAll())
				.thenReturn(Arrays.asList(new Product(5L, "005", "Colgate", 2000.0, "Colgate", 100),
						new Product(2L, "002", "Meat", 3000.0, "Meat", 200)));

		Product product = new Product(5L, "005", "Colgate", 2000.0, "Colgate", 100);
		Mockito.when(productRepository.findById(5L)).thenReturn(Optional.of(product));
	}

	@Test
	public void whenFindProductIsPresent() {
		Optional<Product> product = productService.findById(5L);
		assertTrue("Product Not found", product.isPresent());
	}

	@Test
	public void whenFindAllProductEqualLenght() {
		List<Product> list = productService.findAll();
		assertEquals(2, list.size());
	}

}
