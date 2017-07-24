package com.tmon.platform.api.service;

import java.sql.SQLException;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import com.tmon.platform.api.dto.BasketDto;
import com.tmon.platform.api.exception.CustomException;
import com.tmon.platform.api.exception.RangeNotSatisfyException;
import com.tmon.platform.api.exception.SQLCustomException;

public interface BasketService {
	public JSONObject addBasket(String user_id, int product_id, int quantity) throws SQLCustomException, RangeNotSatisfyException;
	public List<BasketDto> basket(String user_id);
	public JSONObject removeBasket(String user_id, int product_id) ;
	
	//@Transactional(rollbackFor=CustomException.class)
	public JSONObject cleanBasket(String user_id) ;
	public JSONObject incQuantity(String user_id, int product_id) throws RangeNotSatisfyException;
	public JSONObject decQuantity(String user_id, int product_id) throws RangeNotSatisfyException;
	public int getBasketQuantity(String user_id, int product_id);
	public int getMaxQuantity(int product_id);
}
