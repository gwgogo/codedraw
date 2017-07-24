package com.tmon.platform.api.dao;

import java.util.List;

import org.json.simple.JSONObject;

import com.tmon.platform.api.dto.BasketDto;

public interface BasketDao {
	public void addBasket(String user_id, int product_id, int quantity);
	public List<BasketDto> basket(String user_id);
	public void removeBasket(String user_id, int product_id);
	public void cleanBasket(String user_id);
	public void incQuantity(String user_id, int product_id);
	public void decQuantity(String user_id, int product_id);
	public int getBasketQuantity(String user_id, int product_id);
	public int getMaxQuantity(int product_id);
}
