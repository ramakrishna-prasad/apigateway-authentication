package com.gateway.zuulserver.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gateway.zuulserver.jwt.entity.User;
import com.gateway.zuulserver.jwt.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;


	public User save(User user) {
		return userRepository.save(user);
	}

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

}
