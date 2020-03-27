package com.projectone.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectone.model.LoginTemplate;
import com.projectone.model.Reimbursement;
import com.projectone.model.ReimbursementStatus;
import com.projectone.model.ReimbursementTemplate;
import com.projectone.model.ReimbursementType;
import com.projectone.services.ReimbursementService;
import com.projectone.services.UserService;
import com.projectone.util.ConnectionUtil;
import com.projectone.util.JSONConverter;

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
		String action = request.getParameter("action");

		switch(action) {
			case "update":updateRecord(request, response);
				break;
			case "fetchall": fetchAll(request, response);
				break;

			case "fetchfor": fetchFor(request, response);
				break;

		}

	}



	public void fetchFor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	
		PrintWriter pw = response.getWriter();
		
		List<Reimbursement> reimbs = this.rs.findAllForUser(Long.parseLong(request.getParameter("uid")));
		
		reimbs.forEach(r->{System.out.println(r.toJSONString());});
		
		String array = JSONConverter.writeListToJsonArray(reimbs);
		
		pw.println(array);
		
	}

	
	public void fetchAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	
		PrintWriter pw = response.getWriter();
		
		List<Reimbursement> reimbs = this.rs.fetchAll();
		
		reimbs.forEach(r->{System.out.println(r.toJSONString());});
		
		String array = JSONConverter.writeListToJsonArrayWithButtons(reimbs);
		
		pw.println(array);
		
	}
	
	
	public void updateRecord(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		long resolverId = Long.parseLong(request.getParameter("resolver"));
		long reimbId = Long.parseLong(request.getParameter("reimbid"));
		
		String status = request.getParameter("status");
		int statusId = ReimbursementStatus.valueOf(status).ordinal()+1; //calculate actual database status ID from enum
		
		int result = this.rs.updateReimbursement(resolverId, statusId, reimbId);
		
		PrintWriter pw = response.getWriter();
		logger.info("{updates:"+result+"}");
		pw.println("{updates:"+result+"}");
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		
		Reimbursement reimb = new Reimbursement();
		
		//load request into a template object
		ReimbursementTemplate reimbTemp = this.objectMapper.readValue(request.getInputStream(), ReimbursementTemplate.class);
		//reimb.setReceiptBlob(request.getPart("receipt"));
		reimb.setAmount(reimbTemp.getAmount());
		  reimb.setDescription(reimbTemp.getDesc());
		  reimb.setStatus(ReimbursementStatus.PENDING);
		  reimb.setType(ReimbursementType.valueOf(reimbTemp.getType()));
		  
		boolean added = this.rs.addNewReimbursement(reimbTemp.getUid(), reimb);
		if(added) {
			logger.info("New Reimbursement Added!");
		}else {
			logger.info("Add reimbursement FAILED!");
		}
		PrintWriter pw = response.getWriter();
		
		List<Reimbursement> reimbs = this.rs.findAllForUser(reimbTemp.getUid());
		
		reimbs.forEach(r->{System.out.println(r.toJSONString());});
		
		String array = JSONConverter.writeListToJsonArray(reimbs);
		
		pw.print(array);
		
	}

}
