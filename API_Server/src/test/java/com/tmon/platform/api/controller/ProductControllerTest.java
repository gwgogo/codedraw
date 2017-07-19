package com.tmon.platform.api.controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.tmon.platform.api.dto.OrderProductDto;
import com.tmon.platform.api.dto.ProductDto;
import com.tmon.platform.api.exception.CustomException;
import com.tmon.platform.api.service.ProductService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
"file:src/test/resources/testServlet-context.xml",
"file:src/test/resources/testDataSource-context.xml" })
public class ProductControllerTest {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	
	@Autowired
	private ProductService productService;
	
	@Test
	public void productAll() {
		List<ProductDto> list = productService.productAll();
		assertThat(list, notNullValue());
		logger.info(list.get(0).getProduct_name());
	}
	
	@Test
	public void productByProductId() throws CustomException {
		ProductDto productDto = productService.productByProductId(1);
		assertThat(productDto, notNullValue());
	}
	
	@Test
	public void productByCategoryId() throws CustomException {
		List<ProductDto> list = productService.productByCategoryId(1);
		assertThat(list,notNullValue());
	}
	
	@Test
	public void productByReservationId() throws CustomException {
		List<OrderProductDto> list = productService.productByReservationId(1);
		assertThat(list,notNullValue());
	}
	
}
