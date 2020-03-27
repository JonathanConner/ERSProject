package com.projectone.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.projectone.dao.ReimbursementDAOImpl;
import com.projectone.model.Reimbursement;
import com.projectone.model.ReimbursementStatus;
import com.projectone.model.ReimbursementType;

public class ReimbursementTestCase {

	private ReimbursementDAOImpl rdao = new ReimbursementDAOImpl();
	
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
		
		assertTrue(this.rdao.insert(3, reimb));
		
	}

}
