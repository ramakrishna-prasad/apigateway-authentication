package com.gateway.zuulserver.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gateway.zuulserver.dao.RoleDao;
import com.gateway.zuulserver.dao.UserDao;
import com.gateway.zuulserver.entity.Role;
import com.gateway.zuulserver.entity.User;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;

	public User createNewUser(User user) {
		return userDao.save(user);

	}


	public void initRolesAndUser() {

		Role userRole = new Role();

		userRole.setRoleName("User");
		userRole.setRoleDescription("Default role for newly created record");
		roleDao.save(userRole);

		Role adminRole = new Role();

		adminRole.setRoleName("Admin");
		adminRole.setRoleDescription("Admin role");
		roleDao.save(adminRole);

		User user = new User();

		user.setFirstName("Sai Rama Krishna");
		user.setUsername("Malladi Sai Rama Krishna");
		user.setLastName("Malladi");
		user.setUserPassword("Malladi@pass");

		Set<Role> userRoles = new HashSet<Role>();

		userRoles.add(userRole);

		user.setRoles(userRoles);

		userDao.save(user);

		User adminUser = new User();

		adminUser.setFirstName("admin");
		adminUser.setUsername("admin123");
		adminUser.setLastName("admin");
		adminUser.setUserPassword("admin@pass");

		Set<Role> adminRoles = new HashSet<Role>();

		adminRoles.add(adminRole);

		user.setRoles(adminRoles);

		userDao.save(adminUser);

	}

}
