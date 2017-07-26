package com.tmon.platform.api.dto;

public class ProductDto extends BaseProductDto{
	
	private int max_quantity;
	private int sell_quantity;
	private int category_id;
	private String category_name; 
	
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
	public int getCategory_id() {
		return category_id;
	}
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	
	
}
