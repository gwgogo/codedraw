package com.tmon.platform.api.service.impl;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmon.platform.api.dao.ProductDao;
import com.tmon.platform.api.dto.OrderProductDto;
import com.tmon.platform.api.dto.ProductDto;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.exception.SQLCustomException;
import com.tmon.platform.api.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductDao productDao;
	
	
	public List<ProductDto> productAll(){
		
		return productDao.productAll();
	}
	
	public ProductDto productByProductId(int productID) throws NullCustomException {
		
		ProductDto dto = productDao.productByProductId(productID);
		if(dto == null) {
			throw new NullCustomException(604, "Invalid ProductID");
		}
		return dto;
	}
	
	public List<OrderProductDto> productByReservationID(int reservationID) throws NullCustomException{
		List<OrderProductDto> list = productDao.productByReservationID(reservationID);
		if(list.isEmpty()) {
			throw new NullCustomException(604, "Invalid reservationID");
		}
		return list;
	}
	
	public List<ProductDto> productByCategoryID(int categoryID) throws NullCustomException{
		List<ProductDto> list = productDao.productByCategoryID(categoryID); 
		if(list.isEmpty()) {
			throw new NullCustomException(604, "Invalid CategoryID");
		}
		return list;
	}
	
	public JSONObject deleteProduct(int productID) throws NullCustomException {
		
		if(productByProductId(productID) != null) {
			try{
				productDao.deleteProduct(productID);
			}catch(Exception e) {
				throw new NullCustomException(605, "Fail : Product Delete SQL Error");
			}
		}
		JSONObject obj = new JSONObject();
		obj.put("msg", "Success Product Delete");
		return obj;
	}
	
	
}
