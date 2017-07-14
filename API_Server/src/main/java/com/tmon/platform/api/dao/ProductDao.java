package com.tmon.platform.api.dao;

import java.util.List;

import com.tmon.platform.api.dto.ReservationProductDto;
import com.tmon.platform.api.dto.ProductDto;

public interface ProductDao {
	public List<ProductDto> productAll();
	public ProductDto productByProductId(int product_id);
	public List<ReservationProductDto> productByReservationId(int reservation_id);
	public List<ProductDto> productByCategoryId(int category_id);
	public void productDelete(int product_id);
}
