package com.projectone.model.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

import org.apache.logging.log4j.LogManager;

import com.projectone.model.DomainObject;
import com.projectone.model.Role;
import com.projectone.model.User;
import com.projectone.model.UserStatus;
import com.projectone.util.ConnectionUtil;

public class UserMapper extends AbstractMapper {

	public UserMapper() {
		super();
		this.logger = (org.apache.logging.log4j.core.Logger) LogManager.getLogger(UserMapper.class);
	}

	public User find(long id) {
		return (User) abstractFind(id); // Cast DomainObject to User
	}

	public static String insertStatement() {
		return "INSERT INTO ers_users (ERS_USERNAME, ERS_PASSWORD, USER_FIRST_NAME, USER_LAST_NAME, USER_EMAIL, USER_ROLE_ID) VALUES(?, ?, ?, ?, ?, ?)";
	}

	@Override
	protected String findStatement() {
		return "SELECT u.ERS_USERS_ID, u.ERS_USERNAME, u.ERS_PASSWORD, u.USER_FIRST_NAME, u.USER_LAST_NAME, u.USER_EMAIL, r.USER_ROLE FROM ers_users u, ers_user_roles r WHERE u.ers_users_id = ? AND r.ERS_USER_ROLE_ID = u.USER_ROLE_ID";
	}

	@Override
	protected DomainObject doLoad(long id, ResultSet rs) throws SQLException {
		logger.info("doLoad()");
		String username = rs.getString("ers_username");
		String password = rs.getString("ers_password");
		String email = rs.getString("user_email");
		String first_name = rs.getString("user_first_name");
		String last_name = rs.getString("user_last_name");
		String user_role = rs.getString("user_role");

		Role role = Role.valueOf(user_role);

		return new User(id, username, password, email, first_name, last_name, role);

	}

	public static boolean insert(String... input) {

		try (Connection conn = ConnectionUtil.getConnection()) {

			PreparedStatement stmt = conn.prepareStatement(insertStatement());

			stmt.setString(1, input[0]);
			stmt.setString(2, input[1]);
			stmt.setString(3, input[2]);
			stmt.setString(4, input[3]);
			stmt.setString(5, input[4]);
			stmt.setInt(6, 3);

			stmt.execute();
			return true;
		} catch (SQLException sqle) {
			System.out.println(sqle);
		}
		return false;
	}

	@Override
	public void update(DomainObject arg) {
		// do later
	}

	public User findByUserName(String username) {
		String sql = "SELECT * FROM ers_users WHERE ers_username = ? && DISTINCT(user_email)";
		try (Connection conn = ConnectionUtil.getConnection()) {

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, username);

			ResultSet rs = stmt.executeQuery();
			rs.next();
			User user = new User(
					rs.getLong(1), 
					rs.getString(2), 
					rs.getString(3), 
					rs.getString(4), 
					rs.getString(5),
					rs.getString("USER_EMAIL"), 
					Role.Employee
			);
			return user;
			
		} catch (SQLException sqle) {
			logger.warn(sqle);
		}
		
		return null;
	}

}
