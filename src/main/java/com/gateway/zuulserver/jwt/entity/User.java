package com.gateway.zuulserver.jwt.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Setter
@Getter
@Table(name = "usertable")
public class User {
	@Id
	private String username;
	private String password;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "USER_ROLE", joinColumns = {

			@JoinColumn(name = "USER_ID") }, inverseJoinColumns = {

					@JoinColumn(name = "ROLE_ID") })
	private Set<Role> roles;

	public enum RoleName {

		USER, ADMIN
	}

}
