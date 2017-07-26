package com.tmon.platform.api.dto;

public class BasketDto extends BaseProductDto{
	private String user_id;
	private int quantity;
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
