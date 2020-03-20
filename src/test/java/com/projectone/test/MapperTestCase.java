package com.projectone.test;

import static org.junit.Assert.*;

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
import com.projectone.security.SecurityService;

public class MapperTestCase {

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
	public void testMapperFind() {
		User user = (User) this.am.find(1);

		System.out.println(user.toJSONString());

	}

	@Test
	public void testMapperInsert() {
//		String[] str = new String[6];
//		str[0] = "thisisatest";
//		str[1] = "password";
//		str[2] = "test@test.com";
//		str[3] = "Test";
//		str[4] = "User";
//
//		if (this.am.insert(str))
//			System.out.println("REGISTER COMPLETE");

	}

	@Test
	public void testPasswordHash() {

		Scanner in = new Scanner(System.in);

		System.out.println("Raw String value is: \n\t");

		String password = in.nextLine();

		String secure = this.sec.hashPassword(password);

		System.out.println("Secure hashed value! :");
		System.out.println(secure);
		System.out.println("Hash length: ");
		System.out.println(secure.length());

		LogManager.getLogger(MapperTestCase.class).info(secure);

	}

	@Test
	public void testUserAuthentication() {

		Scanner in = new Scanner(System.in);

		System.out.println("Username:");

		String username = in.nextLine();

		System.out.println("Password:");

		String password = in.nextLine();

		User user = this.am.findByUserName(username);
		
		assertNotNull(user);
		
		if(this.sec.checkPassword(password, user.getPassword().toString())) {
			LogManager.getLogger(MapperTestCase.class).info("User has Authenticated!");
		}
		
		
		
		
		
	}

}
