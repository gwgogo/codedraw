package com.tmon.platform.api.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmon.platform.api.dao.BasketDao;
import com.tmon.platform.api.dto.BasketDto;
import com.tmon.platform.api.service.BasketService;

@Service
public class BasketServiceImpl implements BasketService {
	
	@Autowired
	BasketDao basketDao;
	
	public JSONObject addBasket(BasketDto basketDto) throws SQLException{
		JSONObject obj = new JSONObject();
		try{
			basketDao.addBasket(basketDto);
			obj.put("msg", "Success : Insert Basket");
		}catch(Exception e) {
			throw new SQLException("Basket Insert Error");
		}
		return obj;
	}
	
	public List<BasketDto> basket(String user_id){
		return basketDao.basket(user_id);
	}
}
