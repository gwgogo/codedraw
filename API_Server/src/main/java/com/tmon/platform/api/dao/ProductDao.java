package com.tmon.platform.api.dao;

import java.util.List;

import com.tmon.platform.api.dto.ProductDto;

public interface ProductDao {
	public List<ProductDto> productAll();
	public List<ProductDto> productByOrderId(int order_id);
	public List<ProductDto> productByCategoryId(int category_id);
}
