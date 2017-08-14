package com.tmon.platform.api.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tmon.platform.api.dao.ProductDao;
import com.tmon.platform.api.dto.ProductDto;
import com.tmon.platform.api.dto.OrderProductDto;

@Repository
public class ProductDaoImpl implements ProductDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<ProductDto> productAll(){
		return sqlSession.selectList("ProductMapper.productAll");
	}
	
	
	public ProductDto productByProductId(int productID) {
		return sqlSession.selectOne("ProductMapper.productByProductId",productID);
	}
	
	
	public List<ProductDto> productByCategoryID(int categoryID){
		return sqlSession.selectList("ProductMapper.productByCategoryId", categoryID);
	}
}
