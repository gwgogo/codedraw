package com.tmon.platform.api.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmon.platform.api.dao.ProductDao;
import com.tmon.platform.api.dto.ProductDto;
import com.tmon.platform.api.dto.OrderProductDto;
import com.tmon.platform.api.exception.CustomException;
import com.tmon.platform.api.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductDao productDao;
	
	public List<ProductDto> productAll(){
		return productDao.productAll();
	}
	
	public ProductDto productByProductId(int product_id) throws CustomException {
		ProductDto dto = productDao.productByProductId(product_id);
		if(dto == null) {
			throw new CustomException(501, "Invalid ProductID");
		}
		return dto;
	}
	
	public List<OrderProductDto> productByReservationId(int reservation_id) throws CustomException{
		List<OrderProductDto> list = productDao.productByReservationId(reservation_id);
		if(list == null) {
			throw new CustomException(501, "Invalid ReservationID");
		}
		return list;
	}
	
	public List<ProductDto> productByCategoryId(int category_id) throws CustomException{
		List<ProductDto> list = productDao.productByCategoryId(category_id);
		if(list == null) {
			throw new CustomException(501, "Invalid CategoryID");
		}
		return list;
	}
	
	public JSONObject deleteProduct(int product_id) throws SQLException {
		JSONObject obj = new JSONObject();
		try {
			productDao.deleteProduct(product_id);
			obj.put("msg", "success delete");
		}catch(Exception e) {
			throw new SQLException("fail delete");
		}
		return obj;
	}
	
	
}
