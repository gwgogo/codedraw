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
	public void addBasket(String userID, int productID, int quantity) {
		Map<String, String> map = new HashMap<>();
		map.put("userID", userID);
		map.put("productID", String.valueOf(productID));
		map.put("quantity", String.valueOf(quantity));
		sqlSession.insert("BasketMapper.addBasket", map);
	}


	public List<BasketDto> basket(String userID){
		return sqlSession.selectList("BasketMapper.basket", userID);
	}
	
	
	public int removeBasket(String userID, int productID) {
		Map<String, String> map = new HashMap<>();
		map.put("userID", userID);
		map.put("productID", String.valueOf(productID));
		return sqlSession.delete("BasketMapper.removeBasket", map);
	}
	
	public void cleanBasket(String userID) {
		sqlSession.delete("BasketMapper.cleanBasket", userID);
	}
	
	public void incQuantity(String userID, int productID) {
		Map<String, String> map = new HashMap<>();
		map.put("userID", userID);
		map.put("productID", String.valueOf(productID));
		sqlSession.update("BasketMapper.incQuantity", map);
	}
	
	public void decQuantity(String userID, int productID) {
		Map<String, String> map = new HashMap<>();
		map.put("userID", userID);
		map.put("productID", String.valueOf(productID));
		sqlSession.update("BasketMapper.decQuantity", map);
	}
	
	public int getBasketQuantity(String userID, int productID) {
		Map<String, String> map = new HashMap<>();
		map.put("userID", userID);
		map.put("productID", String.valueOf(productID));
		return sqlSession.selectOne("BasketMapper.getBasketQuantity", map);
	}
	
	public Integer getMaxQuantity(int productID) {
		return sqlSession.selectOne("BasketMapper.getMaxQuantity", productID);
	}
}
