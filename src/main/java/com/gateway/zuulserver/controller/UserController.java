package com.gateway.zuulserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.gateway.zuulserver.entity.User;
import com.gateway.zuulserver.service.UserService;

import jakarta.annotation.PostConstruct;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@PostConstruct
	private void initRolesAndUser() {

		userService.initRolesAndUser();
	}

	@PostMapping("/createNewUser")
	private ResponseEntity<User> createNewUser(@RequestBody User user) {

		User userApi = userService.createNewUser(user);
		return new ResponseEntity<User>(userApi, HttpStatus.OK);

	}

	@GetMapping("/forAdmin")
	private String forAdmin() {

		return "This url is only accessible to admin.";
	}

	@GetMapping("/forUser")
	private String forUser() {

		return "This url is only accessible to user.";
	}
}
