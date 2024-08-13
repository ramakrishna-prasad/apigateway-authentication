package com.gateway.zuulserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Setter
@Getter
public class Role {
	

	@Id
	private String roleName;
	private String roleDescription;

}
