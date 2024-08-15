package com.gateway.zuulserver.jwt.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequestDTO {

	private String username;
	private String password;
    @JsonProperty("isAdmin")
	private boolean isAdmin;
}