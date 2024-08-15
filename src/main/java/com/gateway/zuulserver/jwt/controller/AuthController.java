package com.gateway.zuulserver.jwt.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gateway.zuulserver.jwt.entity.Role;
import com.gateway.zuulserver.jwt.entity.User;
import com.gateway.zuulserver.jwt.entity.User.RoleName;
import com.gateway.zuulserver.jwt.model.AuthRequestDTO;
import com.gateway.zuulserver.jwt.model.JwtResponseDTO;
import com.gateway.zuulserver.jwt.repository.RoleRepository;
import com.gateway.zuulserver.jwt.service.JwtService;
import com.gateway.zuulserver.jwt.service.UserService;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/api/v1/")
public class AuthController {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostConstruct
	public void dataInitializer() {

		if (roleRepository.findByName(RoleName.USER).isEmpty()) {
			roleRepository.save(new Role(RoleName.USER));
		}
		if (roleRepository.findByName(RoleName.ADMIN).isEmpty()) {
			roleRepository.save(new Role(RoleName.ADMIN));
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequestDTO authenticationRequest)
			throws Exception {
		final String jwt = jwtService.createJwtToken(authenticationRequest);
		return ResponseEntity.ok(new JwtResponseDTO(jwt));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody AuthRequestDTO authenticationRequest) {
		if (userService.findByUsername(authenticationRequest.getUsername()) != null) {
			return ResponseEntity.badRequest().body("Username is already taken.");
		}
		User user = new User();
		user.setUsername(authenticationRequest.getUsername());
		user.setPassword(passwordEncoder.encode(authenticationRequest.getPassword()));

		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName(RoleName.USER)
				.orElseThrow(() -> new RuntimeException("User role not found"));
		roles.add(userRole);

		if (authenticationRequest.isAdmin()) {
			Role adminRole = roleRepository.findByName(RoleName.ADMIN)
					.orElseThrow(() -> new RuntimeException("Admin role not found"));
			roles.add(adminRole);
		}

		user.setRoles(roles);
		userService.save(user);
		return ResponseEntity.ok("User registered successfully.");
	}
}