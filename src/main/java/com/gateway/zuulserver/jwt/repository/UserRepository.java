package com.gateway.zuulserver.jwt.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gateway.zuulserver.jwt.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

	User findByUsername(String username);

}
