package com.tmon.platform.api.dao;

import java.util.List;

import com.tmon.platform.api.dto.ProductDto;

public interface ProductDao {
	public List<ProductDto> selectProductListAll();
	public List<ProductDto> selectProductListByUserId(String user_id);
	public List<ProductDto> selectProductListByOrderId(int order_id);
	public List<ProductDto> selectProductListByCategoryId(int category_id);
}
