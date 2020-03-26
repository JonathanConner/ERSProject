package com.projectone.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		
		Reimbursement reimb = new Reimbursement();
		
		//load request into a template object
		ReimbursementTemplate reimbTemp = this.objectMapper.readValue(request.getInputStream(), ReimbursementTemplate.class);

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
		pw.print("[");
		reimbs.forEach(r->{pw.print(r.toJSONString()+",");});
		pw.print("]");
		
		
	}

}
