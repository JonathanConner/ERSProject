/**
 * 
 */
package com.projectone.security;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
/**
 * @author Jon
 *
 */
public class SecurityService {

	private static  Logger logger = (Logger)LogManager.getLogger(SecurityService.class);

	private Authenticator auth;

	/**
	 * SecurityService default constructor
	 */
	public SecurityService() {
		super();
	}

	/**
	 * SecurityService constructor with hasher injection
	 * 
	 * @param hasher
	 */
	public SecurityService(Authenticator auth) {
		super();
		this.auth = auth;
	}

	public static String hashPassword(String raw) {
		PBKDF2Hasher hasher = new PBKDF2Hasher(20);
		char[] ch = raw.toCharArray();
		System.out.println(ch.length);
		System.out.println("the char array"+ch);
		System.out.println("the char array [0]"+ch[0]);
		
		return hasher.hash(ch); // the hashed password
	}

	public boolean checkPassword(String raw, String hashed) {

		PBKDF2Hasher hasher = new PBKDF2Hasher();
		System.out.println();
		return hasher.checkPassword(raw.toCharArray(), hashed);// should be true

	}

}
