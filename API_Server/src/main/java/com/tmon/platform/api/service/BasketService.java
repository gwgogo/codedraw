package com.tmon.platform.api.service;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

import com.tmon.platform.api.dto.BasketDto;
import com.tmon.platform.api.exception.CustomException;

public interface BasketService {
	public JSONObject addBasket(String user_id, int product_id, int quantity) throws SQLException;
	public List<BasketDto> basket(String user_id);
	public JSONObject removeBasket(String user_id, int product_id) throws CustomException;
	public JSONObject cleanBasket(String user_id) throws CustomException;
	public JSONObject incQuantity(String user_id, int product_id) throws CustomException;
	public JSONObject decQuantity(String user_id, int product_id) throws CustomException;
	
}
