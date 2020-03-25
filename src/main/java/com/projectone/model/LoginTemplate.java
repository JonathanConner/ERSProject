package com.projectone.model;


public class LoginTemplate {

	private String username;
	private String password;
	
	public LoginTemplate() {
		super();
	}

	public LoginTemplate(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginTemplate [username=" + username + ", password=" + password + "]";
	}

}
