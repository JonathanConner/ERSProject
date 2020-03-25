package com.projectone.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectone.model.Reimbursement;
import com.projectone.model.ReimbursementType;
import com.projectone.services.ReimbursementService;
import com.projectone.services.UserService;

/**
 * Servlet implementation class ReimbursementServlet
 */
public class ReimbursementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	private ObjectMapper objectMapper = null;

	private ReimbursementService rs;

	private Logger logger;
	
	public ReimbursementServlet() {
		super();
		this.objectMapper = new ObjectMapper();
		this.rs = new ReimbursementService();
		this.logger = LogManager.getLogger();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json");
		
		Reimbursement reimb = new Reimbursement();
		
		reimb.setDescription(request.getParameter("desc"));
		reimb.setAmount(Integer.parseInt(request.getParameter("amount")));
		reimb.setType(ReimbursementType.valueOf(request.getParameter("type")));
		
		
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
