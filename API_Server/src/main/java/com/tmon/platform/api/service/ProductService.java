package com.tmon.platform.api.service;

import java.util.List;

import com.tmon.platform.api.dto.ProductDto;

public interface ProductService {
	public List<ProductDto> selectProductListAll();
	public List<ProductDto> selectProductListByUserId(String user_id);
	public List<ProductDto> selectProductListByOrderId(int order_id);
	public List<ProductDto> selectProductListByCategoryId(int category_id);
}
