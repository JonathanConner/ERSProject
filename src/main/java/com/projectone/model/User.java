
package com.projectone.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectone.security.Password;

/**
 * 
 * This is the User model class
 * 
 * @author Jonathan Conner <jonathan.g.conner@gmail.com>
 * 
 */
@JsonIgnoreProperties({"password"})
public class User extends DomainObject{

	private long id;
	private String username;
	private Password password = new Password();
	private String firstName;
	private String lastName;
	private String email;

	private Role role;

	private UserStatus status;
	
	private List<Reimbursement> reimbursements;
	
	/**
	 * User constructor with JsonCreator annotation
	 * @param id
	 * @param username
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param status
	 */
	@JsonCreator
	public User(
			@JsonProperty("id") long id, 
			@JsonProperty("username") String username, 
			@JsonProperty("password") String password, 
			@JsonProperty("firstName") String firstName, 
			@JsonProperty("lastName") String lastName, 
			@JsonProperty("email") String email,
			@JsonProperty("role") Role role
	) {
		super();
		this.id = id;
		this.username = username;
		this.password.setValue(password);
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role = role;
		this.reimbursements = new ArrayList<Reimbursement>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Password getPassword() {
		return password;
	}

	public void setPassword(String value) {
		this.password.setValue(value);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getUserRole() {
		return role;
	}

	public void setUserRole(Role userRole) {
		this.role = userRole;
	}

	public UserStatus getStatus() {

		return status;
	}
	

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	
	public List<Reimbursement> getReimbursements() {
		return this.reimbursements;
	}

	public void addReimbursement(Reimbursement reimb) {
		this.reimbursements.add(reimb);
	}

	
	
	@Override
	public int hashCode() {
		return Objects.hash(email, firstName, id, lastName, password, role, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		return Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName) && id == other.id
				&& Objects.equals(lastName, other.lastName) && Objects.equals(password, other.password)
				&& Objects.equals(role, other.role) && Objects.equals(username, other.username);
	}

	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", email=" + email + ", role=" + role + "]";
	}

	
	public String toJSONString() {
		

		ObjectMapper objectMapper = new ObjectMapper();
		
	    String json = null;
		try {
			json = objectMapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return json;
	}
	
	
}
