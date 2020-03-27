package com.projectone.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectone.dao.UserDAOImpl;
import com.projectone.model.LoginTemplate;
import com.projectone.model.ReimbursementType;
import com.projectone.model.Role;
import com.projectone.model.User;
import com.projectone.services.UserService;
import com.projectone.util.ConnectionUtil;

public class AuthServlet extends HttpServlet {

	private static final long serialVersionUID = -2592072522031966678L;

	private ObjectMapper objectMapper = null;

	private UserService us;

	private Logger logger;
	public AuthServlet() {
		super();
		this.objectMapper = new ObjectMapper();
		this.us = new UserService();
		this.logger = LogManager.getLogger();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		
		String action = req.getParameter("action");

		switch(action) {
			case "logout":logout(req, resp);
				break;
		}
		
	}
	
	public void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		  HttpSession session = req.getSession();
		  
		  session.invalidate();

		  resp.getWriter().println("logout success");

		  
		
	}
	
	/****
	 * This Method Is Called By The Servlet Container To Process A 'POST' Request.
	 ****/
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	

		  LoginTemplate template = this.objectMapper.readValue(req.getInputStream(), LoginTemplate.class);
		  
		  System.out.println(template.toString());
		  
		  User user = this.us.authenticateUser(template.getUsername(), template.getPassword());
		  
		  HttpSession session = req.getSession();
	      
	      session.setAttribute("currentUser", user);
	      
	      resp.getWriter().println(user.toJSONString());
	}
}