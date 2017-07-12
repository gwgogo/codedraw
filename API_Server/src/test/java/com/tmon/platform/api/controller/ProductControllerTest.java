package com.tmon.platform.api.controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tmon.platform.api.dto.ProductDto;
import com.tmon.platform.api.service.ProductService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
"file:src/test/resources/testServlet-context.xml",
"file:src/test/resources/testDataSource-context.xml" })
public class ProductControllerTest {

	@Autowired
	ProductService productService;
	
	@Test
	public void selectProductListAll() {
		List<ProductDto> list = productService.productAll();
		assertThat(list, notNullValue());
	}
}
