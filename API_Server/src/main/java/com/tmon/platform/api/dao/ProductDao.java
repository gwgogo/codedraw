package com.tmon.platform.api.dao;

import java.sql.SQLException;
import java.util.List;

import org.json.simple.JSONObject;

import com.tmon.platform.api.dto.OrderProductDto;
import com.tmon.platform.api.dto.ProductDto;

public interface ProductDao {
	public List<ProductDto> productAll();
	public ProductDto productByProductId(int productID);
	public List<OrderProductDto> productByReservationID(int reservationID);
	public List<ProductDto> productByCategoryID(int categoryID);
	public void deleteProduct(int productID);
}
