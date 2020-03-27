package com.projectone.dao;

import java.util.List;

import com.projectone.model.Reimbursement;

public interface ReimbursementDAO {
	public List<Reimbursement> findAll();
	public List<Reimbursement> findAllReimbursementsForUser(long uid);
	public boolean insert(long userid, Reimbursement reimb);
	public int update(long resolverid,  int status, long reimbid);

}
