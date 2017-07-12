package com.tmon.platform.api.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tmon.platform.api.dao.ProductDao;
import com.tmon.platform.api.dto.ProductDto;

@Repository
public class ProductDaoImpl implements ProductDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<ProductDto> productAll(){
		return sqlSession.selectList("ProductMapper.productAll");
	}
	
	public List<ProductDto> productByOrderId(int order_id){
		return sqlSession.selectList("ProductMapper.productByOrderId", order_id);
	}
	
	public List<ProductDto> productByCategoryId(int category_id){
		return sqlSession.selectList("ProductMapper.productByCategoryId", category_id);
	}
}
