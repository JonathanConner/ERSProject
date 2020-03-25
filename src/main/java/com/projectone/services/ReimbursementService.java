package com.projectone.services;

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
	
	
	
}
