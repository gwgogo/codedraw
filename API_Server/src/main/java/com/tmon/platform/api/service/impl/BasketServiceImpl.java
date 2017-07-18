package com.tmon.platform.api.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmon.platform.api.dao.BasketDao;
import com.tmon.platform.api.dto.BasketDto;
import com.tmon.platform.api.exception.CustomException;
import com.tmon.platform.api.service.BasketService;

@Service
public class BasketServiceImpl implements BasketService {
	
	@Autowired
	BasketDao basketDao;
	
	public JSONObject addBasket(String user_id, int product_id, int quantity) throws SQLException{
		JSONObject obj = new JSONObject();
		try{
			basketDao.addBasket(user_id, product_id, quantity);
			obj.put("msg", "Success : Insert Basket");
		}catch(Exception e) {
			throw new SQLException("Basket Insert Error");
		}
		return obj;
	}
	
	public List<BasketDto> basket(String user_id){
		return basketDao.basket(user_id);
	}
	
	public JSONObject removeBasket(String user_id, int product_id) throws CustomException {
		try {
			basketDao.removeBasket(user_id, product_id);
		}catch(Exception e) {
			throw new CustomException(501, "removeBasket Error");
		}
		JSONObject obj = new JSONObject();
		obj.put("msg", "Success incQuantity");
		return obj;
	}
	
	public JSONObject incQuantity(String user_id, int product_id) throws CustomException {
		
		try {
			basketDao.incQuantity(user_id, product_id);	
		}catch(Exception e) {
			throw new CustomException(501, "incQuantity Error");
		}
		JSONObject obj = new JSONObject();
		obj.put("msg", "Success incQuantity");
		return obj;
	}
	
	public JSONObject decQuantity(String user_id, int product_id) throws CustomException {
		try {
			basketDao.decQuantity(user_id, product_id);
		}catch(Exception e) {
			throw new CustomException(501, "decQuantity Error");
		}
		JSONObject obj = new JSONObject();
		obj.put("msg", "Success incQuantity");
		return obj;
	}
	
	
}
