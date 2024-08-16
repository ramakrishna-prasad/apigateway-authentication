package com.gateway.zuulserver.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.gateway.zuulserver.jwt.JwtUtil;
import com.gateway.zuulserver.jwt.model.AuthRequestDTO;

@Component
public class JwtService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;


	@Autowired
	private JwtUtil jwtUtil;

	public String createJwtToken(AuthRequestDTO authenticationRequest) throws Exception {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
				authenticationRequest.getPassword()));

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		return jwtUtil.generateToken(userDetails);
	}
}
