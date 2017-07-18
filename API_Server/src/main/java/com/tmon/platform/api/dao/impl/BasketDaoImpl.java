package com.tmon.platform.api.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tmon.platform.api.dao.BasketDao;
import com.tmon.platform.api.dto.BasketDto;

@Repository
public class BasketDaoImpl implements BasketDao {

	@Autowired
	SqlSession sqlSession;
	
	@Override
	public void addBasket(String user_id, int product_id, int quantity) {
		Map<String, String> map = new HashMap<>();
		map.put("user_id", user_id);
		map.put("product_id", String.valueOf(product_id));
		map.put("quantity", String.valueOf(quantity));
		sqlSession.insert("BasketMapper.addBasket", map);
	}


	public List<BasketDto> basket(String user_id){
		return sqlSession.selectList("BasketMapper.basket", user_id);
	}
	
	public void removeBasket(String user_id, int product_id) {
		Map<String, String> map = new HashMap<>();
		map.put("user_id", user_id);
		map.put("product_id", String.valueOf(product_id));
		sqlSession.delete("BasketMapper.removeBasket", map);
	}
	
	public void incQuantity(String user_id, int product_id) {
		Map<String, String> map = new HashMap<>();
		map.put("user_id", user_id);
		map.put("product_id", String.valueOf(product_id));
		sqlSession.update("BasketMapper.incQuantity", map);
	}
	
	public void decQuantity(String user_id, int product_id) {
		Map<String, String> map = new HashMap<>();
		map.put("user_id", user_id);
		map.put("product_id", String.valueOf(product_id));
		sqlSession.update("BasketMapper.decQuantity", map);
	}
	
}
