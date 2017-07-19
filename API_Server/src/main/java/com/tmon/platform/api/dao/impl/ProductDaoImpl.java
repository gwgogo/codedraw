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
	
	
	public ProductDto productByProductId(int product_id) {
		return sqlSession.selectOne("ProductMapper.productByProductId",product_id);
	}
	
	
	public List<OrderProductDto> productByReservationId(int reservation_id){
		return sqlSession.selectList("ProductMapper.productByReservationId", reservation_id);
	}
	
	
	public List<ProductDto> productByCategoryId(int category_id){
		return sqlSession.selectList("ProductMapper.productByCategoryId", category_id);
	}
	
	
	public void deleteProduct(int product_id) {
		sqlSession.delete("ProductMapper.deleteProduct", product_id);
	}
	
	
}
