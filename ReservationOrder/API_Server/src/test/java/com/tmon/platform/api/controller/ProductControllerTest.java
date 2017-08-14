package com.tmon.platform.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Product 조회관련은 로그인이 필요 없기 때문에 로그인 없이 파라미터 validation만 함
 * @author gwlee
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
"file:src/test/resources/testServlet-context.xml",
"file:src/test/resources/testDataSource-context.xml"})
public class ProductControllerTest {
	@Autowired
	public WebApplicationContext wac;
	public MockMvc mockMvc;
	public String validSession;
	private static final String PREFIX = "/products";
	private static final int PRODUCT_ID = 2;
	private static final int INVALID_PRODUCT_ID = 9999999;
	private static final int CATEGORY_ID = 2;
	private static final int INVALID_CATEGORY_ID = 22222222;
	
	@Before
	public void 목업데이터초기화() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@After
	public void 목업데이터해제() {
		mockMvc = null;
		validSession = null;
	}
	
	@Test
	public void 전체조회() throws Exception {
		mockMvc.perform(get(PREFIX))
		.andDo(print());
	}
	
	@Test
	public void 정상상품상세조회() throws Exception {
		mockMvc.perform(get(PREFIX + "/" + PRODUCT_ID))
		.andDo(print());
	}
	
	@Test
	public void 없는상품상세조회() throws Exception {
		mockMvc.perform(get(PREFIX + "/" + INVALID_PRODUCT_ID))
		.andDo(print())
		.andExpect(jsonPath("errCode").value(604));
	}
	
	@Test
	public void 숫자말고영어넣어서상세조회() throws Exception {
		mockMvc.perform(get(PREFIX + "/" + "ABC"))
		.andDo(print())
		.andExpect(jsonPath("errCode").value(623));
	}
	
	@Test
	public void 스페이스바넣어서상세조회() throws Exception {
		mockMvc.perform(get(PREFIX + "/" + " "))
		.andDo(print())
		.andExpect(jsonPath("errCode").value(623));
	}
	
	@Test
	public void 슬래시두개넣고상세조회() throws Exception {
		mockMvc.perform(get(PREFIX + "//" + " "))
		.andDo(print())
		.andExpect(jsonPath("errCode").value(623));
	}
	
	@Test
	public void 정상카테고리별조회() throws Exception {
		mockMvc.perform(get(PREFIX + "/category/" + CATEGORY_ID))
		.andDo(print());
	}
	
	@Test
	public void 없는카테고리로조회() throws Exception {
		mockMvc.perform(get(PREFIX + "/category/" + INVALID_CATEGORY_ID))
		.andDo(print())
		.andExpect(jsonPath("errCode").value(604));
	}
	
	@Test
	public void 인자없이카테고리별조회() throws Exception {
		mockMvc.perform(get(PREFIX + "/category/"))
		.andDo(print())
		.andExpect(jsonPath("errCode").value(623));
	}
	
	@Test
	public void 영어인자로카테고리별조회() throws Exception {
		mockMvc.perform(get(PREFIX + "/category/" + "abc"))
		.andDo(print())
		.andExpect(jsonPath("errCode").value(623));
	}
}
