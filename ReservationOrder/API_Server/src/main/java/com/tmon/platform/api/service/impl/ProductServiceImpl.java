package com.tmon.platform.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmon.platform.api.dao.ProductDao;
import com.tmon.platform.api.dto.ProductDto;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductDao productDao;
	
	/**
	 * @author 신광원
	 * @description 상품 전체 목록 가져오기
	 */
	public List<ProductDto> productAll(){
		return productDao.productAll();
	}
	
	
	/**
	 * @author 신광원
	 * @description 해당 상품의 상세 내용 가져오기
	 */
	public ProductDto productByProductId(int productID) throws NullCustomException {		
		ProductDto dto = productDao.productByProductId(productID);
		if(dto == null) {
			throw new NullCustomException(604, "Invalid ProductID");
		}
		return dto;
	}
	
	
	/**
	 * @author 신광원
	 * @description 카테고리에 따른 상풍 목록 가져오기
	 */
	public List<ProductDto> productByCategoryID(int categoryID) throws NullCustomException{
		List<ProductDto> list = productDao.productByCategoryID(categoryID); 
		if(list.isEmpty()) {
			throw new NullCustomException(604, "Invalid CategoryID");
		}
		return list;
	}
	
}
