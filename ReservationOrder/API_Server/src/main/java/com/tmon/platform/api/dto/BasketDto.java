package com.tmon.platform.api.dto;

public class BasketDto extends BaseProductDto{
	private String userID;
	private int quantity;
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
