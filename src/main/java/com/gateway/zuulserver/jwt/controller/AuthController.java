package com.gateway.zuulserver.jwt.controller;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/")
public class AuthController {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
    @Qualifier("bcryptEncoder")
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
		logger.debug("JWT access token has been created", jwt);
		return ResponseEntity.ok(new JwtResponseDTO(jwt));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/user/details")
	public ResponseEntity<?> getUserDetails(@RequestParam String username) {
		User user = userService.findByUsername(username);
		return ResponseEntity.ok(user);
	}

	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody AuthRequestDTO authenticationRequest) {
		if (userService.findByUsername(authenticationRequest.getUsername()) != null) {
			return ResponseEntity.badRequest().body("Username is already taken.");
		}
		User user = new User();
		user.setUsername(authenticationRequest.getUsername());
		String encodedPassword = passwordEncoder.encode(authenticationRequest.getPassword());
		user.setPassword(encodedPassword);
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
		logger.debug("User is successfully saved.");
		return ResponseEntity.ok("User registered successfully.");
	}
}