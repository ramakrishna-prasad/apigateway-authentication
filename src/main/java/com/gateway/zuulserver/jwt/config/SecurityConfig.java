package com.gateway.zuulserver.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.gateway.zuulserver.jwt.JwtAuthFilter;
import com.gateway.zuulserver.jwt.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Autowired
	JwtAuthFilter jwtAuthFilter;

	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;

//authorization
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable()) // Disable CSRF protection
				.authorizeHttpRequests(authorizeRequests -> authorizeRequests
						.requestMatchers("/api/v1/signup", "/public/**").permitAll() // Allow access to signup and
						.requestMatchers("/api/v1/login", "/public/**").permitAll()						// public endpoints without
																						// authentication
						.anyRequest().authenticated() // Require authentication for all other endpoints
				).sessionManagement(
						sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Use
																														// stateless
																														// sessions
				).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // Add JWT
																								// filter
				.build(); // Build the SecurityFilterChain
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsServiceImpl);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;

	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
