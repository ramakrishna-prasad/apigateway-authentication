package com.gateway.zuulserver.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gateway.zuulserver.entity.User;

@Repository
public interface UserDao extends CrudRepository<User, String> {

}
