package com.tmon.platform.api.service.impl;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmon.platform.api.dao.BasketDao;
import com.tmon.platform.api.dto.BasketDto;
import com.tmon.platform.api.exception.CustomException;
import com.tmon.platform.api.exception.RangeNotSatisfyException;
import com.tmon.platform.api.exception.SQLCustomException;
import com.tmon.platform.api.service.BasketService;

@Service
public class BasketServiceImpl implements BasketService {
	
	
	@Autowired
	BasketDao basketDao;
	
	public JSONObject addBasket(String user_id, int product_id, int quantity) throws SQLCustomException, RangeNotSatisfyException{
		
		int maxQuantity = getMaxQuantity(product_id);
		if(quantity > maxQuantity || quantity <= 0) {
			throw new RangeNotSatisfyException(609, "Quantity Range Error");
		}
		
		try{
			basketDao.addBasket(user_id, product_id, quantity);
		}catch(Exception e) {
			throw new SQLCustomException(608, "Fail : Insert Basket SQL Error");
		}
		JSONObject obj = new JSONObject();
		obj.put("msg", "Success : Insert Basket");
		return obj;
	}
	
	
	public List<BasketDto> basket(String user_id){
		return basketDao.basket(user_id);
	}
	
	
	// 어떠한 경우에 customexception을 발생시켜야 할지 고민해볼 것 
	public JSONObject removeBasket(String user_id, int product_id) {
		basketDao.removeBasket(user_id, product_id);
		
		JSONObject obj = new JSONObject();
		obj.put("msg", "Success Remove Basket");
		return obj;
	}
	
	// 장바구니가 비어있을 때는 clean이 에러를 내야 하나?
	public JSONObject cleanBasket(String user_id) {
		basketDao.cleanBasket(user_id);
		JSONObject obj = new JSONObject();
		obj.put("msg", "Success Clean Basket");
		return obj;
	}
	
	
	public JSONObject incQuantity(String user_id, int product_id) throws RangeNotSatisfyException {
		int maxQuantity = getMaxQuantity(product_id);
		int basketQuantity = getBasketQuantity(user_id, product_id);
		
		// 장바구니 상품의 수량은 재고량을 초과할 수 없음
		if(basketQuantity >= maxQuantity) {
			throw new RangeNotSatisfyException(609, "Can't Over MaxQuantity");
		}
		
		basketDao.incQuantity(user_id, product_id);	
		
		JSONObject obj = new JSONObject();
		obj.put("msg", "Success Inc Quantity");
		return obj;
	}
	
	
	public JSONObject decQuantity(String user_id, int product_id) throws RangeNotSatisfyException {
		int quantity = getBasketQuantity(user_id, product_id);

		// 장바구니 상품의 수량 체크 - 0보다 작게 할 수 없음
		if(quantity <= 0) {		
			throw new RangeNotSatisfyException(609, "Can't Under ZeroQuantity");
		}
		basketDao.decQuantity(user_id, product_id);
		
		JSONObject obj = new JSONObject();
		obj.put("msg", "Success Dec Quantity");
		return obj;
	}
	
	
	public int getBasketQuantity(String user_id, int product_id){
		return basketDao.getBasketQuantity(user_id, product_id);
	}
	
	
	public int getMaxQuantity(int product_id) {
		return basketDao.getMaxQuantity(product_id);
	}
	
}
