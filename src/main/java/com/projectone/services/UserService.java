package com.projectone.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.projectone.dao.UserDAOImpl;
import com.projectone.model.User;
import com.projectone.security.PBKDF2Hasher;
import com.projectone.security.SecurityService;

public class UserService {
	protected Logger logger;

	protected UserDAOImpl udao;
	
	protected SecurityService sec;
	
	public UserService() {
		super();
		this.logger = LogManager.getLogger(UserService.class);
		this.udao = new UserDAOImpl();
		this.sec = new SecurityService();
	}

	

	public User authenticateUser(String username, String password) {
		String un = username;
		User user = this.udao.findByUserName(un);
		if(user !=null) {
			boolean check = this.sec.checkPassword(password, user.getPassword());
			if(!check) {
				logger.warn("Username and password missmatch!");
				return user;
			}else {
				logger.info("User authenticated!");
				return user;
			}
		}
		logger.warn("Username: "+username +" NOT FOUND!");
		return null;
	}
	
}
