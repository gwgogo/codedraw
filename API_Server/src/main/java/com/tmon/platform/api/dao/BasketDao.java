package com.tmon.platform.api.dao;

import java.util.List;

import org.json.simple.JSONObject;

import com.tmon.platform.api.dto.BasketDto;

public interface BasketDao {
	public void addBasket(String userID, int productID, int quantity);
	public List<BasketDto> basket(String userID);
	public int removeBasket(String userID, int productID);
	public void cleanBasket(String userID);
	public void incQuantity(String userID, int productID);
	public void decQuantity(String userID, int productID);
	public int getBasketQuantity(String userID, int productID);
	public Integer getMaxQuantity(int productID);
}
