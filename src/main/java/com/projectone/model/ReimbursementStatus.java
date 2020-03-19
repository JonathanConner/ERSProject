package com.projectone.model;

public class ReimbursementStatus extends DomainObject{
	private int id;
	private String status;
	
	/**
	 * @param id
	 * @param status
	 */
	public ReimbursementStatus(int id, String status) {
		super();
		this.id = id;
		this.status = status;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
