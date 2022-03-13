package com.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class InventoryApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(InventoryApplication.class, args);
	}

	@Autowired
	Environment env;

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(getListProperty("cors.allowed.origins"));
		config.setAllowedMethods(getListProperty("cors.allowed.methods"));
		config.setAllowedHeaders(getListProperty("cors.allowed.headers"));
		config.setExposedHeaders(getListProperty("cors.exposed.headers"));
		config.setAllowCredentials(getBooleanProperty("cors.allow.credentials"));
		config.setMaxAge(getLongProperty("cors.maxage"));
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(InventoryApplication.class);
	}

	private List<String> getListProperty(String key) {
		return Arrays.asList(env.getProperty(key).split(","));
	}

	private Boolean getBooleanProperty(String key) {
		return Boolean.valueOf(env.getProperty(key));
	}

	private Long getLongProperty(String key) {
		return Long.valueOf(env.getProperty(key));
	}

	@Bean
	public ModelMapper modelMapper() {
    return new ModelMapper();
	}
}
