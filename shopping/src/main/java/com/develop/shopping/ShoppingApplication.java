package com.develop.shopping;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.develop.shopping.domain.Product;
import com.develop.shopping.domain.User;
import com.develop.shopping.repository.ProductRepository;
import com.develop.shopping.repository.UserRepository;

@SpringBootApplication
public class ShoppingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(ProductRepository repository, BCryptPasswordEncoder byBCryptPasswordEncoder,
			UserRepository userRepository) {
		return (args) -> {
			userRepository.save(new User(1L, "admin", byBCryptPasswordEncoder.encode("123456"), "Dario Perez"));
			repository.save(new Product(1L, "001", "Colgate", 30.0, "Colgate", 1000));
			repository.save(new Product(2L, "002", "Foot Loops", 120.0, "Colgate", 1000));
			repository.save(new Product(3L, "003", "Meat", 150.0, "Colgate", 1000));
			repository.save(new Product(4L, "004", "Computer", 1050.0, "Computer", 1000));
			repository.save(new Product(5L, "005", "Mouse", 10.0, "Mouse", 1000));
		};
	}
}
