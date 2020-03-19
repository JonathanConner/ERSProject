package com.projectone.model;

public class ReimbursementType extends DomainObject {
	private int id;
	private String type;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @param type
	 */
	public ReimbursementType(String type) {
		super();
		this.type = type;
	}
}
