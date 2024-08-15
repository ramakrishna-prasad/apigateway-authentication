package com.gateway.zuulserver.jwt.entity;

import com.gateway.zuulserver.jwt.entity.User.RoleName;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Setter
@Getter
public class Role {
	public Role() {
	}

	// Constructor to initialize the role
	public Role(RoleName name) {
		this.name = name;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private RoleName name; // Make sure this property exists

	// Getters and setters
}
