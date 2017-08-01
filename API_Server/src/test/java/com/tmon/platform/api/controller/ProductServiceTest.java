package com.tmon.platform.api.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.apache.ibatis.jdbc.Null;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.tmon.platform.api.dto.OrderProductDto;
import com.tmon.platform.api.dto.ProductDto;
import com.tmon.platform.api.exception.AbstractCustomException;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.service.ProductService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
"file:src/test/resources/testServlet-context.xml",
"file:src/test/resources/testDataSource-context.xml",
"file:src/test/resources/testDateFormat-context.xml"})
public class ProductServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceTest.class);
	
	private static final int VALID_PRODUCT_ID = 1;		// 1 ~ 10
	private static final int INVALID_PRODUCT_ID = 100;	// other
	
	private static final int VALID_CATEGORY_ID = 1;		// 1 ~ 7
	private static final int INVALID_CATEGORY_ID = 100;	// other
	
	private static final int VALID_RESERVATION_ID = 50;
	private static final int INVALID_RESERVATION_ID = 10000;
	
	@Autowired
	private ProductService productService;

	
	@Test
	public void productAll() {
		List<ProductDto> list = productService.productAll();
		assertThat(list, notNullValue());
		logger.info(list.get(0).getProductName());
	}
	
	@Test
	public void productByValidProductId() throws NullCustomException {
		ProductDto productDto = productService.productByProductId(VALID_PRODUCT_ID);
		assertEquals(productDto, notNullValue());
	}
	
	@Test(expected=NullCustomException.class)
	public void productByInvalidProductId() throws NullCustomException {
		ProductDto productDto = productService.productByProductId(INVALID_PRODUCT_ID);
		assertEquals(productDto, notNullValue());
	}
	
	@Test
	public void productByValidCategoryId() throws NullCustomException  {
		List<ProductDto> list = productService.productByCategoryID(VALID_CATEGORY_ID);
		assertThat(list,notNullValue());
	}
	
	@Test(expected=MethodArgumentTypeMismatchException.class)
	public void productByCategoryId() throws NullCustomException  {
		
		String requestParam = "aa";
		int cateogry_id = Integer.parseInt(requestParam);
		List<ProductDto> list = productService.productByCategoryID(cateogry_id);
		assertThat(list,notNullValue());
	
	}
	
	
	@Test(expected=NullCustomException.class)
	public void productByInvalidCategoryId() throws NullCustomException  {
		List<ProductDto> list = productService.productByCategoryID(INVALID_CATEGORY_ID);
		assertThat(list,notNullValue());
	}
	
	
	@Test
	public void productByValidReservation_id() throws NullCustomException  {
		List<OrderProductDto> list = productService.productByReservationID(VALID_RESERVATION_ID);
		assertThat(list,notNullValue());
	}
	
	
	@Test(expected=NullCustomException.class)
	public void productByInvalidReservation_id() throws NullCustomException  {
		List<OrderProductDto> list = productService.productByReservationID(INVALID_RESERVATION_ID);
		assertThat(list,notNullValue());
	}
}
