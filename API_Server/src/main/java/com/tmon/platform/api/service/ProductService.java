package com.tmon.platform.api.service;

import java.sql.SQLException;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestParam;

import com.tmon.platform.api.dto.ReservationProductDto;
import com.tmon.platform.api.dto.ProductDto;

public interface ProductService {
	public List<ProductDto> productAll();
	public ProductDto productByProductId(int product_id);
	public List<ReservationProductDto> productByReservationId(int reservation_id);
	public List<ProductDto> productByCategoryId(int category_id);
	public JSONObject deleteProduct(int product_id) throws SQLException;
	
}
