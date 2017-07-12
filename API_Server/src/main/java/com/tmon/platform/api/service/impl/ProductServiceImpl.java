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
	
	public List<ProductDto> productAll(){
		return productDao.productAll();
	}
	
	public List<ProductDto> productByOrderId(int order_id){
		return productDao.productByOrderId(order_id);
	}
	
	public List<ProductDto> productByCategoryId(int category_id){
		return productDao.productByCategoryId(category_id);
	}
}
