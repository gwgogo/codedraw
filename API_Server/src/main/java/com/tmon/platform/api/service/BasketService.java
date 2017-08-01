package com.tmon.platform.api.service;

import java.util.List;

import org.json.simple.JSONObject;

import com.tmon.platform.api.dto.BasketDto;
import com.tmon.platform.api.exception.AbstractCustomException;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.exception.RangeNotSatisfyException;
import com.tmon.platform.api.exception.SQLCustomException;

public interface BasketService {
	public JSONObject addBasket(String userID, int productID, int quantity) throws SQLCustomException, RangeNotSatisfyException, NullCustomException;
	public List<BasketDto> basket(String userID);
	public JSONObject removeBasket(String userID, int productID) throws NullCustomException;
	
	//@Transactional(rollbackFor=CustomException.class)
	public JSONObject cleanBasket(String userID);
	public JSONObject incQuantity(String userID, int productID) throws RangeNotSatisfyException, NullCustomException;
	public JSONObject decQuantity(String userID, int productID) throws RangeNotSatisfyException, NullCustomException;
	
	
	
	/* 테스트코드 위해서 잠시 인터페이스에 추가해둠 - 배포할 떄는 삭제하고 impl에서 private로 변경*/
	public int getBasketQuantity(String userID, int productID) throws NullCustomException;
	public Integer getMaxQuantity(int productID) throws NullCustomException;
}
