package com.tmon.platform.api.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmon.platform.api.dao.ProductDao;
import com.tmon.platform.api.dto.OrderProductDto;
import com.tmon.platform.api.dto.ProductDto;
import com.tmon.platform.api.exception.ProductException;
import com.tmon.platform.api.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductDao productDao;
	
	public List<ProductDto> productAll(){
		return productDao.productAll();
	}
	
	public ProductDto productByProductId(int product_id) throws ProductException {
		ProductDto dto = productDao.productByProductId(product_id);
		if(dto == null) {
			throw new ProductException(604, "Invalid ProductID");
		}
		return dto;
	}
	
	public List<OrderProductDto> productByReservationId(int reservation_id) throws ProductException{
		List<OrderProductDto> list = productDao.productByReservationId(reservation_id);
		if(list == null) {
			throw new ProductException(604, "Invalid ReservationID");
		}
		return list;
	}
	
	public List<ProductDto> productByCategoryId(int category_id) throws ProductException{
		List<ProductDto> list = productDao.productByCategoryId(category_id);
		if(list == null) {
			throw new ProductException(604, "Invalid CategoryID");
		}
		return list;
	}
	
	public JSONObject deleteProduct(int product_id) throws ProductException {
		JSONObject obj = new JSONObject();
		try {
			productDao.deleteProduct(product_id);
			obj.put("msg", "Success Product Delete");
		}catch(Exception e) {
			throw new ProductException(605, "Fail Product Delete");
		}
		return obj;
	}
	
	
}
