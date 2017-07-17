package com.tmon.platform.api.dao.impl;

import java.util.List;

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
	public void addBasket(BasketDto basketDto) {
		sqlSession.insert("BasketMapper.addBasket", basketDto);
	}


	public List<BasketDto> basket(String user_id){
		return sqlSession.selectList("BasketMapper.basket", user_id);
	}
	
	
}
