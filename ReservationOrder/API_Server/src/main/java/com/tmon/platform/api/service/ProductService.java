package com.tmon.platform.api.service;

import java.util.List;

import com.tmon.platform.api.dto.ProductDto;
import com.tmon.platform.api.exception.NullCustomException;

public interface ProductService {
	public List<ProductDto> productAll();
	public ProductDto productByProductId(int productID) throws NullCustomException;
	public List<ProductDto> productByCategoryID(int categoryID) throws NullCustomException;
}
