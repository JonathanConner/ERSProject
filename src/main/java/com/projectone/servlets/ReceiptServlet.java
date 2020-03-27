package com.projectone.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.projectone.util.ConnectionUtil;

/**
 * Servlet implementation class Receipt
 */
@MultipartConfig
public class ReceiptServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	private Logger logger;
    /**
     */
    public ReceiptServlet() {
        super();
		this.logger = LogManager.getLogger();
    }

	/**
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * Handle receipt uploads from mulitpart form
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		uploadReceipt(request, response);
	}

	public void uploadReceipt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		//Get a scanner for the first part of the form
		
		Part reimbId = request.getPart("reimbId");
        Scanner scanner  = new Scanner( reimbId.getInputStream());
        String reimb_id = scanner.nextLine(); 
		
		Part receipt = request.getPart("receipt");
		
		String sql = "UPDATE ERS_REIMBURSEMENT SET REIMB_RECEIPT = ? WHERE REIMB_ID = ?";
			try (Connection conn = ConnectionUtil.getConnection()) {
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setBinaryStream(1, receipt.getInputStream(), (int)  receipt.getSize());//Set the image data to binary stream
				stmt.setString(2, reimb_id);
				stmt.executeUpdate();
		        conn.commit();
		        System.out.println("RECEIPT SUBMITTED!");
			}catch(SQLException sqle) {
				
				logger.warn(sqle);
			}
		
		scanner.close();
		PrintWriter pw = response.getWriter();

		pw.println("Reimbursement receipt submitted!");
		
	}

}
