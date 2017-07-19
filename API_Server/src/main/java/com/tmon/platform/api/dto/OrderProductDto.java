package com.tmon.platform.api.dto;

public class OrderProductDto {
	
	private int reservation_id;
	private int product_id;
	private String product_name;
	private String image_url;
	private int quantity;
	private int part_price; 		// 상품가격 * 수량
	
	public int getReservation_id() {
		return reservation_id;
	}
	public void setReservation_id(int reservation_id) {
		this.reservation_id = reservation_id;
	}
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public int getPart_price() {
		return part_price;
	}
	public void setPart_price(int part_price) {
		this.part_price = part_price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	
}
