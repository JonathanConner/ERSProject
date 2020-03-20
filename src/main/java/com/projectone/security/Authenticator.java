
package com.projectone.security;

import org.apache.logging.log4j.LogManager;

import com.projectone.model.User;
import com.projectone.model.UserStatus;

/**
 * @author Jonathan Conner <jonathan.g.conner@gmail.com>
 *
 */
public class Authenticator {

	private static org.apache.logging.log4j.Logger logger = LogManager.getLogger(Authenticator.class);

	private PBKDF2Hasher hasher;

	public Authenticator() {
		super();
	}

	/**
	 * Authenticator with hasher injection
	 * 
	 * @param hasher
	 */
	public Authenticator(PBKDF2Hasher hasher) {
		super();
		this.hasher = hasher;
	}

	public PBKDF2Hasher getHasher() {
		return hasher;
	}

	public void setHasher(PBKDF2Hasher hasher) {
		this.hasher = hasher;
	}

	/**
	 * Returns the user or null if authentication failed
	 * 
	 * @param user
	 * @param suppliedPassword
	 * @return
	 */
	public User auth(User user, Password suppliedPassword) {

		String username = user.getUsername();

		if (user != null && suppliedPassword != null) {

			logger.info("Authenticating " + username + " via username and password...");

			String hashed = hasher.hash(suppliedPassword.getValue().toCharArray());
			
			if ((user.getPassword().equals(hashed))) 
				logger.info("Valid password for " + username + " supplied!");
			else 
				logger.warn("User " + username + " failed to authenticate! (Invalid password)"); // Fail on password message
			
			logger.info(username + " has been Authenticated!"); //Success message

			user.setStatus(UserStatus.LOGGEDIN);
			return user;
			
		} else {
			logger.warn("User " + username + " failed to authenticate!");
			
			return null;
		}
		
	}

}
