package com.projectone.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.projectone.dao.ReimbursementDAOImpl;
import com.projectone.model.Reimbursement;
import com.projectone.model.ReimbursementStatus;
import com.projectone.model.ReimbursementType;
import com.projectone.services.ReimbursementService;

public class ReimbursementTestCase {

	private ReimbursementDAOImpl rdao = new ReimbursementDAOImpl();
	
	private ReimbursementService rs = new ReimbursementService();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCanCreateReimbursement() {
		
		Reimbursement reimb = new Reimbursement();
		reimb.setAmount(1000);
		reimb.setDescription("This is a test reimbursement");
		reimb.setStatus(ReimbursementStatus.PENDING);
		reimb.setType(ReimbursementType.LODGING);
		
		assertTrue(this.rdao.insert(1, reimb));
		
	}
	
	@Test
	public void testCanFetchAllReimbursements() {
		List<Reimbursement> list = this.rs.fetchAll();
		
		assertTrue(list.get(1) instanceof Reimbursement);
		
	}
	
	
	
	@Test
	public void testCanFetchAllReimbursementsForUser() {
		List<Reimbursement> list = this.rs.findAllForUser(3);
		
		assertTrue(list.get(1) instanceof Reimbursement);
		
	}
	
	
	
	
	
	

}
