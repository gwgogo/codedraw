package com.tmon.platform.api.service;

import java.util.List;

import org.json.simple.JSONObject;

import com.tmon.platform.api.dto.OrderProductDto;
import com.tmon.platform.api.dto.ProductDto;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.exception.SQLCustomException;

public interface ProductService {
	public List<ProductDto> productAll();
	public ProductDto productByProductId(int productID) throws NullCustomException;
	public List<OrderProductDto> productByReservationID(int reservationID) throws NullCustomException;
	public List<ProductDto> productByCategoryID(int categoryID) throws NullCustomException;
	public JSONObject deleteProduct(int productID) throws NullCustomException;
	
}
