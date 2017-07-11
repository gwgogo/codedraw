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
	
	public List<ProductDto> selectProductListAll(){
		return sqlSession.selectList("ProductMapper.selectProductListAll");
	}
	
	public List<ProductDto> selectProductListByUserId(String user_id){
		return sqlSession.selectList("ProductMapper.selectProductListByUserId", user_id);
	}
	
	public List<ProductDto> selectProductListByOrderId(int order_id){
		return sqlSession.selectList("ProductMapper.selectProductListByOrderId", order_id);
	}
	
	public List<ProductDto> selectProductListByCategoryId(int category_id){
		return sqlSession.selectList("ProductMapper.selectProductListByCategoryId", category_id);
	}
}
