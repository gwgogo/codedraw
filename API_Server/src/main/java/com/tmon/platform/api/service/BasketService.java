package com.tmon.platform.api.service;

import java.util.List;

import org.json.simple.JSONObject;

import com.tmon.platform.api.dto.BasketDto;
import com.tmon.platform.api.exception.AbstractCustomException;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.exception.RangeNotSatisfyException;
import com.tmon.platform.api.exception.SQLCustomException;

public interface BasketService {
	public JSONObject addBasket(String user_id, int product_id, int quantity) throws SQLCustomException, RangeNotSatisfyException, NullCustomException;
	public List<BasketDto> basket(String user_id);
	public JSONObject removeBasket(String user_id, int product_id) throws NullCustomException;
	
	//@Transactional(rollbackFor=CustomException.class)
	public JSONObject cleanBasket(String user_id);
	public JSONObject incQuantity(String user_id, int product_id) throws RangeNotSatisfyException, NullCustomException;
	public JSONObject decQuantity(String user_id, int product_id) throws RangeNotSatisfyException, NullCustomException;
	
	
	
	/* 테스트코드 위해서 잠시 인터페이스에 추가해둠 - 배포할 떄는 삭제 */
	public int getBasketQuantity(String user_id, int product_id) throws NullCustomException;
	public Integer getMaxQuantity(int product_id) throws NullCustomException;
}
