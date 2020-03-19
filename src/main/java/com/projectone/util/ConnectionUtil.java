package com.projectone.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

	private static Connection conn = null;

	/**
	 * 
	 * @return Connection
	 */
	public static Connection getConnection() {

		String[] creds = System.getenv("DBCreds").split(";");
		try {

			try {
				conn = DriverManager.getConnection(creds[0], creds[1], creds[2]);
			} catch (SQLException se) {
				se.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return conn;

	}

}
