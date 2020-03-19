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

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.log4j.Logger;

/**
 * @author Jon
 *
 */
public class SecurityService {

	private static Logger logger = Logger.getLogger(SecurityService.class);


	private Authenticator auth;
	
	
	/**
	 * SecurityService default constructor
	 */
	public SecurityService() {
		super();
	}

	/**
	 * SecurityService constructor with hasher injection
	 * @param hasher
	 */
	public SecurityService(Authenticator auth) {
		super();
		this.auth = auth;
	}



}
