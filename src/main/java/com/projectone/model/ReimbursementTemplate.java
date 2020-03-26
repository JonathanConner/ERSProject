package com.projectone.model;

public class ReimbursementTemplate {

	public String desc;
	
	public String type;
	
	public double amount;

	public int uid;
	
	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = Double.parseDouble(amount);
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "ReimbursementTemplate [desc=" + desc + ", type=" + type + ", amount=" + amount + "]";
	}
	
	
}
