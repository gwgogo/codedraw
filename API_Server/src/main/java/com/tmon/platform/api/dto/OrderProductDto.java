package com.tmon.platform.api.dto;

public class OrderProductDto extends BaseProductDto{

	private int reservationID;
	private int quantity;
	private String categoryName; // {join CATEGORY}
	public int getReservationID() {
		return reservationID;
	}
	public void setReservationID(int reservationID) {
		this.reservationID = reservationID;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	

}
