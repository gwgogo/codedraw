package com.tmon.platform.api.dao;

import java.util.List;

import com.tmon.platform.api.dto.ProductDto;

public interface ProductDao {
	public List<ProductDto> productAll();
	public ProductDto productByProductId(int productID);
	public List<ProductDto> productByCategoryID(int categoryID);
}
