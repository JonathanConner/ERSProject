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

	/****
	 * This Method Is Called By The Servlet Container To Process A 'POST' Request.
	 ****/
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
//	      LoginTemplate template = new LoginTemplate();
//	      template.setUsername(req.getParameter("username"));
//	      template.setPassword(req.getParameter("password"));
//	      User u = new User(1, template.getUsername(), template.getPassword(), "Matthew", "Oberlies", "matthew.oberlies@revature.com", null);
//	      
		  /*
		   * The below commented out code is used to parse data from the body when not using forms
		   */
//	      BufferedReader reader = req.getReader();
//	      StringBuilder sb = new StringBuilder();
//	
//	      String line = "";
//	      while( (line = reader.readLine()) != null) {
//	    	  sb.append(line);
//	      }
//	      
//	      String body = sb.toString();
//	      logger.info("\n"+body);

		  LoginTemplate template = this.objectMapper.readValue(req.getInputStream(), LoginTemplate.class);
		  
		  
//		  resp.setContentType("application/json");
//		  
		  System.out.println(template.toString());
		  
		  User user = new User();

		  try (Connection conn = ConnectionUtil.getConnection()){

			  PreparedStatement stmt = conn.prepareStatement("SELECT * FROM ERS_USERS WHERE ERS_USERNAME = ?");

				stmt.setString(1, template.getUsername());

				ResultSet rs = stmt.executeQuery();
				rs.next();
				
				user.setId(rs.getLong(1));
				user.setUsername(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setFirstName(rs.getString(4));
				user.setLastName(rs.getString(5));
				user.setEmail(rs.getString(6));
				user.setUserRole(Role.Employee);
				LogManager.getLogger(UserDAOImpl.class).info(user.toJSONString());
				
			}catch(SQLException sqle) {
				System.out.println(sqle);
			}

		  
		  HttpSession session = req.getSession();
	      
	      session.setAttribute("currentUser", user);
	      
	      resp.getWriter().println(user.toJSONString());
	}
}