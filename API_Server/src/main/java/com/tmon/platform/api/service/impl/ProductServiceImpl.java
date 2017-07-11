package com.tmon.platform.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmon.platform.api.dao.ProductDao;
import com.tmon.platform.api.dto.ProductDto;
import com.tmon.platform.api.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductDao productDao;
	
	public List<ProductDto> selectProductListAll(){
		return productDao.selectProductListAll();
	}
	
	public List<ProductDto> selectProductListByUserId(String user_id){
		return productDao.selectProductListByUserId(user_id);
	}
	
	public List<ProductDto> selectProductListByOrderId(int order_id){
		return productDao.selectProductListByOrderId(order_id);
	}
	public List<ProductDto> selectProductListByCategoryId(int category_id){
		return productDao.selectProductListByCategoryId(category_id);
	}
}
