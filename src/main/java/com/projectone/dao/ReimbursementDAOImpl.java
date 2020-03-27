package com.projectone.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.projectone.model.Reimbursement;
import com.projectone.model.ReimbursementStatus;
import com.projectone.model.ReimbursementType;
import com.projectone.model.User;
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

			String sql = "SELECT * FROM view_reimbursements WHERE USER_ROLE_ID = 3";
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(sql);

			
			while(rs.next()) {
				User author = new User();
				author.setId(rs.getLong("ERS_USERS_ID"));
				author.setUsername(rs.getString("ERS_USERNAME"));
			
				Reimbursement reimb = new Reimbursement();
				reimb.setId(rs.getLong("REIMB_ID"));
				reimb.setDescription(rs.getString("REIMB_DESCRIPTION"));
				reimb.setAmount(rs.getDouble("REIMB_AMOUNT"));
				reimb.setSubmittedDate(rs.getTimestamp("REIMB_SUBMITTED"));
				reimb.setResolvedDate(rs.getTimestamp("REIMB_RESOLVED"));
				
				reimb.setAuthor(author);
				
				int status_id = rs.getInt("REIMB_STATUS_ID");
				int type_id = rs.getInt("REIMB_TYPE_ID");
				
				if(status_id == 1) {
					reimb.setStatus(ReimbursementStatus.PENDING);
				}else if(status_id == 2){
					reimb.setStatus(ReimbursementStatus.APPROVED);
				}else {
					reimb.setStatus(ReimbursementStatus.DENIED);
				}
				
				if(type_id == 1)
				{
					reimb.setType(ReimbursementType.TRAVEL);
				}else if(type_id == 2) {
					reimb.setType(ReimbursementType.LODGING);
				}else if(type_id == 3) {
					reimb.setType(ReimbursementType.FOOD);
				}else {
					reimb.setType(ReimbursementType.OTHER);
				}
				
				reimb.setReceiptBlob(rs.getBlob("REIMB_RECEIPT"));
				
				list.add(reimb);
			}
			return list;
		} catch (SQLException sqle) {
		}

		return list;
		
	}

	
	/**
	 * Find all reimbursements for a given user
	 * @param username
	 * @return
	 */
	public List<Reimbursement> findAllReimbursementsForUser(long uid) {

		List<Reimbursement> list = new ArrayList<>();

		try (Connection conn = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM ers_reimbursement WHERE reimb_author = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setLong(1, uid);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Reimbursement reimb = new Reimbursement();
				reimb.setId(rs.getLong("REIMB_ID"));
				reimb.setDescription(rs.getString("REIMB_DESCRIPTION"));
				reimb.setAmount(rs.getDouble("REIMB_AMOUNT"));
				reimb.setSubmittedDate(rs.getTimestamp("REIMB_SUBMITTED"));
				reimb.setResolvedDate(rs.getTimestamp("REIMB_RESOLVED"));
				int status_id = rs.getInt("REIMB_STATUS_ID");
				int type_id = rs.getInt("REIMB_TYPE_ID");
				
				if(status_id == 1) {
					reimb.setStatus(ReimbursementStatus.PENDING);
				}else if(status_id == 2){
					reimb.setStatus(ReimbursementStatus.APPROVED);
				}else {
					reimb.setStatus(ReimbursementStatus.DENIED);
				}
				
				if(type_id == 1)
				{
					reimb.setType(ReimbursementType.TRAVEL);
				}else if(type_id == 2) {
					reimb.setType(ReimbursementType.LODGING);
				}else if(type_id == 3) {
					reimb.setType(ReimbursementType.FOOD);
				}else {
					reimb.setType(ReimbursementType.OTHER);
				}
				
				reimb.setSubmittedDate(rs.getTimestamp("REIMB_SUBMITTED"));
				reimb.setResolvedDate(rs.getTimestamp("REIMB_RESOLVED"));
				reimb.setReceiptBlob(rs.getBlob("REIMB_RECEIPT"));
				
				list.add(reimb);
			}
			return list;
		} catch (SQLException sqle) {
		}

		return list;
	}

	/**
	 * This performs insertion on the database of a new reimbursement
	 */
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
	
	private java.sql.Timestamp getCurrentTimeStamp() {

		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());

	}

	/**
	 * This is performs an update for a specified reimbursement
	 * @return
	 */
	public int update(long resolverid,  int status, long reimbid) {
		
		String sql = "UPDATE ERS_REIMBURSEMENT SET REIMB_RESOLVER = ?, REIMB_STATUS_ID = ?, REIMB_RESOLVED = ? WHERE REIMB_ID = ?";
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			PreparedStatement stmt  = conn.prepareStatement(sql);
		
			stmt.setLong(1, resolverid);
			stmt.setInt(2, status);
			stmt.setTimestamp(3, getCurrentTimeStamp());
			stmt.setLong(4, reimbid);
			
			int rows = stmt.executeUpdate();
			
			return rows;
			
		} catch (SQLException sqle) {
			logger.warn("SQL Exception",sqle);
		}
		
		return 0;
	}
}
