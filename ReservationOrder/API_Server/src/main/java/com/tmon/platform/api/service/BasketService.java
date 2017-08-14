package com.tmon.platform.api.service;

import java.util.List;

import org.json.simple.JSONObject;

import com.tmon.platform.api.dto.BasketDto;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.exception.RangeNotSatisfyException;
import com.tmon.platform.api.exception.SQLCustomException;

public interface BasketService {
	public JSONObject addBasket(String userID, int productID, int quantity) throws SQLCustomException, RangeNotSatisfyException, NullCustomException;
	public List<BasketDto> basket(String userID);
	public JSONObject removeBasket(String userID, int productID) throws NullCustomException;
	
	
	public JSONObject cleanBasket(String userID);
	public JSONObject incQuantity(String userID, int productID) throws RangeNotSatisfyException, NullCustomException;
	public JSONObject decQuantity(String userID, int productID) throws RangeNotSatisfyException, NullCustomException;
	
	
}
