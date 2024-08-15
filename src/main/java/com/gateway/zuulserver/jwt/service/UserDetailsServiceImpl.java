package com.gateway.zuulserver.jwt.service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.gateway.zuulserver.jwt.entity.User;
import com.gateway.zuulserver.jwt.model.CustomUserDetails;
import com.gateway.zuulserver.jwt.repository.UserRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		logger.debug("Entering in loadUserByUsername Method...");
		User user = userRepository.findByUsername(username);
		if (user == null) {
			logger.error("Username not found: " + username);
			throw new UsernameNotFoundException("could not found user..!!");
		}
		logger.info("User Authenticated Successfully..!!!");
		return new CustomUserDetails(user);
	}
}
