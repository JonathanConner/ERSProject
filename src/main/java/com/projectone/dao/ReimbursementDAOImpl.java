package com.projectone.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.projectone.model.Reimbursement;
import com.projectone.util.ConnectionUtil;

public class ReimbursementDAOImpl implements ReimbursementDAO {

	private Logger logger;
	
	public ReimbursementDAOImpl(){
		super();
		this.logger = LogManager.getLogger(ReimbursementDAOImpl.class);
	} 
	
	
	/**
	 * Find all reimbursements for ALL users
	 * @param username
	 * @return
	 */
	public List<Reimbursement> findAll() {

		List<Reimbursement> list = new ArrayList<>();

		try (Connection conn = ConnectionUtil.getConnection()) {

			// Find all reimbursements

		} catch (SQLException sqle) {
		}

		return list;
	}

	
	/**
	 * Find all reimbursements for a given user
	 * @param username
	 * @return
	 */
	public List<Reimbursement> findAllReimbursementsForUser(String username) {

		List<Reimbursement> list = new ArrayList<>();

		try (Connection conn = ConnectionUtil.getConnection()) {

			// Find all reimbursements

		} catch (SQLException sqle) {
		}

		return list;
	}

	public boolean insert(long userid, Reimbursement reimb) {
		String sql = "INSERT INTO ERS_REIMBURSEMENT (REIMB_AMOUNT, REIMB_DESCRIPTION, REIMB_RECEIPT, REIMB_AUTHOR, REIMB_STATUS_ID, REIMB_TYPE_ID) VALUES (?,?,?,?,?,?)";
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			PreparedStatement stmt  = conn.prepareStatement(sql);
			
			stmt.setDouble(1, reimb.getAmount());
			stmt.setString(2, reimb.getDescription());
			stmt.setBlob(3, (Blob)reimb.getReceiptBlob());
			stmt.setLong(4, userid);//author id
			stmt.setInt(5, (reimb.getStatus().ordinal()+1));
			stmt.setInt(6, (reimb.getType().ordinal()+1));
		
			stmt.execute();
			return true;
			
		} catch (SQLException sqle) {
			logger.warn("SQL Exception",sqle);
		}
		
		
		
		return false;
		
	}
	
}
