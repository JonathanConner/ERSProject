package com.projectone.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.projectone.model.Role;
import com.projectone.model.User;
import com.projectone.model.mapper.AbstractMapper;
import com.projectone.model.mapper.UserMapper;
import com.projectone.security.PBKDF2Hasher;
import com.projectone.security.SecurityService;
import com.projectone.util.ConnectionUtil;

public class UserTestCase {

	private UserMapper am;

	private SecurityService sec;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this.sec = new SecurityService();
		this.am = new UserMapper();
	}

	@After
	public void tearDown() throws Exception {
	}



	@Test
	public void testUserAuthentication() {
		String un = "TestEmployee";
		User user = new User();
		try (Connection conn = ConnectionUtil.getConnection()){
			
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM ers_users u WHERE u.ers_username = ?");

			stmt.setString(1, un);

			ResultSet rs = stmt.executeQuery();
			rs.next();
			
			user.setId(rs.getLong(1));
			user.setUsername(rs.getString(2));
			user.setPassword(rs.getString(3));
			user.setFirstName(rs.getString(4));
			user.setLastName(rs.getString(5));
			user.setEmail(rs.getString(6));
			user.setUserRole(Role.Employee);
			
			LogManager.getLogger(UserTestCase.class).info(user.toJSONString());
			
		}catch(SQLException sqle) {
			System.out.println(sqle);
		}
		
		System.out.println(user.toJSONString());
		LogManager.getLogger(UserTestCase.class).info(user.toJSONString());


	}
	@Test
	public void testUserPasswordHash() {
		String raw = "securepassword";
		
		System.out.println("RAW: "+raw);
		
		PBKDF2Hasher pbkdf2 = new PBKDF2Hasher();
		String hashed = pbkdf2.hash(raw.toCharArray());
		System.out.println("HASHED: "+ hashed);

		
		LogManager.getLogger(UserTestCase.class).info(pbkdf2.checkPassword(raw.toCharArray(), hashed));


	}
}
