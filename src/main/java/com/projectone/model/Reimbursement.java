/**
 * 
 */
package com.projectone.model;

import java.sql.Blob;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import oracle.sql.BLOB;

/**
 * @author Jonathan Conner <jonathan.g.conner@gmail.com>
 */
public class Reimbursement extends DomainObject {

	private int id;
	private double amount;
	private Timestamp submittedDate;
	private Timestamp resolvedDate = null;
	private String description;

	/**
	 * Image raw data BLOB
	 */
	private Object receiptBlob;

	/**
	 * The authoring User object
	 * 
	 * FK : REIMB_AUTHOR From table ERS_USERS
	 */
	private User author;

	/**
	 * The resolving User object
	 * 
	 * FK : REIMB_RESOLVER From table ERS_USERS
	 */
	private User resolver;

	/**
	 * The status of the reimbursement
	 * 
	 * FK : REIMB_STATUS_ID From table ERS_REIMBURSEMENT_STATUS
	 */
	private ReimbursementStatus status;

	/**
	 * The type of reimbursement
	 * 
	 * FK : REIMB_TYPE_ID From table ERS_REIMBURSEMENT_TYPE
	 */
	private ReimbursementType type;
	
	
	/**
	 * @param id
	 * @param amount
	 * @param description
	 * @param author
	 * @param resolver
	 * @param status
	 * @param type
	 */
	public Reimbursement(int id, double amount, String description, User author, User resolver,
			ReimbursementStatus status, ReimbursementType type) {
		super();
		this.id = id;
		this.amount = amount;
		this.description = description;
		this.author = author;
		this.resolver = resolver;
		this.status = status;
		this.type = type;
	}

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Timestamp getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Timestamp submittedDate) {
		this.submittedDate = submittedDate;
	}

	public Timestamp getResolvedDate() {
		return resolvedDate;
	}

	public void setResolvedDate(Timestamp resolvedDate) {
		this.resolvedDate = resolvedDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Object getReceiptBlob() {
		return receiptBlob;
	}

	public void setReceiptBlob(Object receiptBlob) {
		this.receiptBlob = receiptBlob;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public User getResolver() {
		return resolver;
	}

	public void setResolver(User resolver) {
		this.resolver = resolver;
	}

	public ReimbursementStatus getStatus() {
		return status;
	}

	public void setStatus(ReimbursementStatus status) {
		this.status = status;
	}

	public ReimbursementType getType() {
		return type;
	}

	public void setType(ReimbursementType type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, author, description, id, receiptBlob, resolvedDate, resolver, status, submittedDate,
				type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Reimbursement)) {
			return false;
		}
		Reimbursement other = (Reimbursement) obj;
		return Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount)
				&& Objects.equals(author, other.author) && Objects.equals(description, other.description)
				&& id == other.id && Objects.equals(receiptBlob, other.receiptBlob)
				&& Objects.equals(resolvedDate, other.resolvedDate) && Objects.equals(resolver, other.resolver)
				&& Objects.equals(status, other.status) && Objects.equals(submittedDate, other.submittedDate)
				&& Objects.equals(type, other.type);
	}

	@Override
	public String toString() {
		return "Reimbursement [id=" + id + ", amount=" + amount + ", submittedDate=" + submittedDate + ", resolvedDate="
				+ resolvedDate + ", description=" + description + ", receiptBlob=" + receiptBlob + ", author=" + author
				+ ", resolver=" + resolver + ", status=" + status + ", type=" + type + "]";
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
