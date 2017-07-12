package com.tmon.platform.api.service;

import java.util.List;

import com.tmon.platform.api.dto.ProductDto;

public interface ProductService {
	public List<ProductDto> productAll();
	public List<ProductDto> productByOrderId(int order_id);
	public List<ProductDto> productByCategoryId(int category_id);
}
