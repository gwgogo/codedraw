package com.tmon.platform.api.dao;

import java.sql.SQLException;
import java.util.List;

import org.json.simple.JSONObject;

import com.tmon.platform.api.dto.OrderProductDto;
import com.tmon.platform.api.dto.ProductDto;

public interface ProductDao {
	public List<ProductDto> productAll();
	public ProductDto productByProductId(int product_id);
	public List<OrderProductDto> productByReservationId(int reservation_id);
	public List<ProductDto> productByCategoryId(int category_id);
	public void deleteProduct(int product_id);
}
