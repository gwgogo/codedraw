package com.tmon.platform.api.dto;

public class ProductDto extends BaseProductDto{
	
	private int maxQuantity;
	private int sellQuantity;
	private int categoryID;
	private String categoryName;
	public int getMaxQuantity() {
		return maxQuantity;
	}
	public void setMaxQuantity(int maxQuantity) {
		this.maxQuantity = maxQuantity;
	}
	public int getSellQuantity() {
		return sellQuantity;
	}
	public void setSellQuantity(int sellQuantity) {
		this.sellQuantity = sellQuantity;
	}
	public int getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	} 
	
	
	
}
