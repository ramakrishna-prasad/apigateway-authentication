package com.gateway.zuulserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gateway.zuulserver.dao.RoleDao;
import com.gateway.zuulserver.entity.Role;

@Service
public class RoleService {

	@Autowired
	private RoleDao roleDao;

	public Role createNewRole(Role role) {

		return roleDao.save(role);

	}

}
