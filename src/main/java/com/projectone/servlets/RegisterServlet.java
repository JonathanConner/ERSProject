package com.projectone.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;

import com.projectone.model.mapper.*;
import com.projectone.security.SecurityService;
import com.projectone.services.UserService;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 3577899497459462918L;


	private org.apache.logging.log4j.Logger logger = LogManager.getLogger(RegisterServlet.class);

	public RegisterServlet() {
		super();

	}

	/**
	 * Handle GET requests and provide a response
	 * 
	 * @param request
	 * @param response
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * Handle POST requests and provide a response
	 * 
	 * @param request
	 * @param response
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String secure = SecurityService.hashPassword(password);

		String email = request.getParameter("email");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		
		UserService us = new UserService();
		us.insert(username, secure, email, firstName, lastName);
		
		
		logger.info(secure);
		logger.info(secure.length());
		logger.info("successful registration");
		response.getWriter().append("Served at: ").append(request.getContextPath());
	
		
	}

}
