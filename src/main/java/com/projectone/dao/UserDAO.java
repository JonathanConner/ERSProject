package com.projectone.dao;

import java.util.List;

import com.projectone.model.User;

public interface UserDAO {
	public boolean insert(String[] input);
	public User findByUserName(String username);
	public List<User> findAll();
}
