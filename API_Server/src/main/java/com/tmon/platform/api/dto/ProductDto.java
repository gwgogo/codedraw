package com.tmon.platform.api.dto;

public class ProductDto {
	private int product_id;
	private String product_name;
	private int price;
	private String image_url;
	private int max_quantity;
	private int sell_quantity;
	private int product_category_category_id;
	private String product_user_user_id;
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
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public int getMax_quantity() {
		return max_quantity;
	}
	public void setMax_quantity(int max_quantity) {
		this.max_quantity = max_quantity;
	}
	public int getSell_quantity() {
		return sell_quantity;
	}
	public void setSell_quantity(int sell_quantity) {
		this.sell_quantity = sell_quantity;
	}
	public int getProduct_category_category_id() {
		return product_category_category_id;
	}
	public void setProduct_category_category_id(int product_category_category_id) {
		this.product_category_category_id = product_category_category_id;
	}
	public String getProduct_user_user_id() {
		return product_user_user_id;
	}
	public void setProduct_user_user_id(String product_user_user_id) {
		this.product_user_user_id = product_user_user_id;
	}
	
}
