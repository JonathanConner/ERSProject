package com.projectone.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.projectone.model.DomainObject;
import com.projectone.model.Role;
import com.projectone.model.User;
import com.projectone.model.mapper.UserMapper;
import com.projectone.util.ConnectionUtil;

public class UserDAOImpl implements UserDAO {
	
	
	protected Logger logger;

	public UserDAOImpl() {
		super();
		this.logger = (org.apache.logging.log4j.core.Logger) LogManager.getLogger(UserMapper.class);
	}

	/**
	 * This is the insert method used in registration
	 * @param input
	 * @return
	 */
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
	
	
	
	/**
	 * Find a user by username
	 * @param username
	 * @return
	 */
	public User findByUserName(String username) {
		String un = username;
		User user = new User();
		try (Connection conn = ConnectionUtil.getConnection()){
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM ers_users u WHERE u.ers_username = ?");

			stmt.setString(1, un);

			ResultSet rs = stmt.executeQuery();
			rs.next();
			user.setId(rs.getLong(1));
			user.setUsername(rs.getString(2));
			user.setPassword(rs.getString("ERS_PASSWORD"));
			user.setFirstName(rs.getString(4));
			user.setLastName(rs.getString(5));
			user.setEmail(rs.getString(6));
			int role_id = rs.getInt("USER_ROLE_ID");
			if(role_id == 1)
			{
				user.setUserRole(Role.Admin);
			}else if(role_id == 2) {
				user.setUserRole(Role.FinManager);
			}else if(role_id == 3) {
				user.setUserRole(Role.Employee);
			}
			LogManager.getLogger(UserDAOImpl.class).info(user.toJSONString());
			
		}catch(SQLException sqle) {
			System.out.println(sqle);
		}

		return user;
	}

	
	public List<User> findAll(){
		List<User> list = new ArrayList<User>();
		
		try (Connection conn = ConnectionUtil.getConnection()){
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM ers_users WHERE user_role_id = 3");

			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				User user = new User();
				user.setId(rs.getLong(1));
				user.setUsername(rs.getString(2));
				user.setPassword(rs.getString("ERS_PASSWORD"));
				user.setFirstName(rs.getString(4));
				user.setLastName(rs.getString(5));
				user.setEmail(rs.getString(6));
				int role_id = rs.getInt("USER_ROLE_ID");
				if(role_id == 1)
				{
					user.setUserRole(Role.Admin);
				}else if(role_id == 2) {
					user.setUserRole(Role.FinManager);
				}else if(role_id == 3) {
					user.setUserRole(Role.Employee);
				}
				list.add(user);
			}
			System.out.println("All employees added to list!");
			
		}catch(SQLException sqle) {
			System.out.println(sqle);
		}

		return list;
	}
	
	
	/**
	 * return the insert statement for new users
	 * @return
	 */
	public static String insertStatement() {
		return "INSERT INTO ers_users (ERS_USERNAME, ERS_PASSWORD, USER_FIRST_NAME, USER_LAST_NAME, USER_EMAIL, USER_ROLE_ID) VALUES(?, ?, ?, ?, ?, ?)";
	}

	
	protected String findStatement() {
		return "SELECT u.ERS_USERS_ID, u.ERS_USERNAME, u.ERS_PASSWORD, u.USER_FIRST_NAME, u.USER_LAST_NAME, u.USER_EMAIL, r.USER_ROLE FROM ers_users u, ers_user_roles r WHERE u.ers_users_id = ? AND r.ERS_USER_ROLE_ID = u.USER_ROLE_ID";
	}
//
//	protected DomainObject doLoad(long id, ResultSet rs) throws SQLException {
//		logger.info("doLoad()");
//		String username = rs.getString("ers_username");
//		String password = rs.getString("ers_password");
//		String email = rs.getString("user_email");
//		String first_name = rs.getString("user_first_name");
//		String last_name = rs.getString("user_last_name");
//		String user_role = rs.getString("user_role");
//
//		Role role = Role.valueOf(user_role);
//
//		return new User(id, username, password, email, first_name, last_name, role);
//
//	}

}
