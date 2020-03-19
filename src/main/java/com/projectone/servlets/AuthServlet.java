package com.projectone.servlets;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthServlet extends HttpServlet {

	private static final long serialVersionUID = -2592072522031966678L;

	private ObjectMapper objectMapper = null;

	public AuthServlet() {
		super();
		this.objectMapper = new ObjectMapper();
	}

	/****
	 * This Method Is Called By The Servlet Container To Process A 'POST' Request.
	 ****/
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		handleRequest(req, resp);
	}

	public void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException {

	}
}