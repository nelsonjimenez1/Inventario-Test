package com.inventory;

import com.inventory.services.UserServiceInterface;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDataBase {

	@Bean
	CommandLineRunner initDatabase(UserServiceInterface userService) {
		return args -> {

		};
	}
}