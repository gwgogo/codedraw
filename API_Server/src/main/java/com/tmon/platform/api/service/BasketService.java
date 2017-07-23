package com.tmon.platform.api.service;

import java.sql.SQLException;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import com.tmon.platform.api.dto.BasketDto;
import com.tmon.platform.api.exception.CustomException;

public interface BasketService {
	public JSONObject addBasket(String user_id, int product_id, int quantity) throws CustomException;
	public List<BasketDto> basket(String user_id);
	public JSONObject removeBasket(String user_id, int product_id) throws CustomException;
	
	//@Transactional(rollbackFor=CustomException.class)
	public JSONObject cleanBasket(String user_id) throws CustomException;
	public JSONObject incQuantity(String user_id, int product_id) throws CustomException;
	public JSONObject decQuantity(String user_id, int product_id) throws CustomException;
	
}
