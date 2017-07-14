package com.tmon.platform.api.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmon.platform.api.dao.ProductDao;
import com.tmon.platform.api.dto.ReservationProductDto;
import com.tmon.platform.api.dto.ProductDto;
import com.tmon.platform.api.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductDao productDao;
	
	public List<ProductDto> productAll(){
		return productDao.productAll();
	}
	
	public ProductDto productByProductId(int product_id) {
		return productDao.productByProductId(product_id);
	}
	
	public List<ReservationProductDto> productByReservationId(int reservation_id){
			
		return productDao.productByReservationId(reservation_id);
	}
	
	public List<ProductDto> productByCategoryId(int category_id){
		return productDao.productByCategoryId(category_id);
	}
	
	public JSONObject productDelete(int product_id) throws SQLException {
		JSONObject obj = new JSONObject();
		try {
			productDao.productDelete(product_id);
			obj.put("msg", "success delete");
		}catch(Exception e) {
			throw new SQLException("fail delete");
		}
		
		return obj;
	}
}
