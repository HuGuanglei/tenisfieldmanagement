package com.cff.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cff.domain.User;

public interface UserDao extends CrudRepository<User, String> {

	User findByUserName(String userName);
	
	List<User> findByRole(String role);
}