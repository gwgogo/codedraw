package com.tmon.platform.api.service;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.tmon.platform.api.dto.ProductDto;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.service.ProductService;


/**
 * @author 신광원
 * @description 상품 목록 조회에 관한 Test Code
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
"file:src/test/resources/testServlet-context.xml",
"file:src/test/resources/testDataSource-context.xml"})
public class ProductServiceTest {
		
	
	@Autowired
	private ProductService productService;

	
	@Test
	public void productAll() {
		List<ProductDto> list = productService.productAll();
		assertThat(list, notNullValue());
	}
	
	@Test 
	public void 유효한상품ID로상품조회() throws NullCustomException {
		ProductDto productDto = productService.productByProductId(1);
		assertThat(productDto, notNullValue());
	}
	
	@Test(expected=NullCustomException.class)
	public void 유효하지않은상품ID로상품조회() throws NullCustomException {
		ProductDto productDto = productService.productByProductId(9999);
	}
	
	
	@Test
	public void 유효한카테고리ID로상품조회() throws NullCustomException  {
		List<ProductDto> list = productService.productByCategoryID(1);
		assertFalse(list.isEmpty());
	}
	
	
	@Test(expected=NullCustomException.class)
	public void 유효하지않은카테고리ID로상품조회() throws NullCustomException  {
		List<ProductDto> list = productService.productByCategoryID(9999);

	}
	
	
}
