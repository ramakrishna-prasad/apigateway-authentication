package com.gateway.zuulserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration {

	private static final String GET = "GET";
	private static final String POST = "POST";
	private static final String DELETE = "DELETE";
	private static final String PUT = "PUT";
	private static final String OPTIONS = "OPTIONS";

	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {

			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:8762").allowedOrigins("https://your-allowed-origin.com")
						.allowedMethods(GET, POST, PUT, DELETE, OPTIONS).allowedHeaders("Content-Type", "Authorization")
						.allowCredentials(true);
			}

		};
	}

}
