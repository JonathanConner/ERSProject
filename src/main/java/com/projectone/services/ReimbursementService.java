package com.projectone.services;

import java.util.List;

import com.projectone.dao.ReimbursementDAOImpl;
import com.projectone.model.Reimbursement;

public class ReimbursementService {

	private ReimbursementDAOImpl rdao;
	
	public ReimbursementService() {
		super();
		this.rdao = new ReimbursementDAOImpl();
	}
	
	public boolean addNewReimbursement(long uid, Reimbursement reimb) {
		
		return this.rdao.insert(uid, reimb);
		
	}
	
	public List<Reimbursement> findAllForUser(long uid){
		
		return this.rdao.findAllReimbursementsForUser(uid);
		
	}
	
	public List<Reimbursement> fetchAll(){
		
		return this.rdao.findAll();
		
	}
	
	/**
	 * returns the number of rows updated
	 * returns 0 for no rows updated
	 * @param uid
	 * @param status
	 * @param rid
	 * @return
	 */
	public int updateReimbursement(long uid, int status, long rid) {
		
		return this.rdao.update(uid, status, rid);
		
	}
	
	
}
