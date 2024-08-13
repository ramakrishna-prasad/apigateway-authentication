package com.gateway.zuulserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gateway.zuulserver.entity.Role;
import com.gateway.zuulserver.service.RoleService;

@RestController
@RequestMapping("/api")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@PostMapping("/createNewRole")
	public ResponseEntity<Role> createNewRole(@RequestBody Role role) {

		Role roleApi = roleService.createNewRole(role);

		return new ResponseEntity<Role>(roleApi, HttpStatus.OK);

	}

}
