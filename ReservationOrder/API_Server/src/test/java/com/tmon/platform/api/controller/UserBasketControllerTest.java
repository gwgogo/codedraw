package com.tmon.platform.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.Cookie;

import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import net.minidev.json.parser.JSONParser;

/**
 * 일반 유저가 접속한 장바구니 컨트롤러 테스트
 * @author gwlee
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
"file:src/test/resources/testServlet-context.xml",
"file:src/test/resources/testDataSource-context.xml"})
public class UserBasketControllerTest {
	@Autowired
	public WebApplicationContext wac;
	public MockMvc mockMvc;
	public String validSession;
	private static final String PREFIX = "/baskets/";
	private static final int TEST_PRODUCT_ID = 2; // [글로벌셀러] 반짝세일! 뉴스킨 베스트 120종
	private static final int TEST_PRODUCT_QUANTITY = 2;
		
	@Before
	public void 상품준비() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		MvcResult response =
				mockMvc.perform(post("/user/login").param("userID", "user0001").param("userPW", "user0001"))
				//.andDo(print())
				.andReturn();
		
		String responseBodstr = response.getResponse().getContentAsString();
		JSONParser parser = new JSONParser();
		JSONObject responseBody = (JSONObject) parser.parse(responseBodstr);
		validSession = responseBody.get("session").toString();
		
		//전체 삭제 후 더미상품 하나만 추가
		mockMvc.perform(delete(PREFIX).header("Cookie", "session=" + validSession).cookie(new Cookie("session", validSession)));
		mockMvc.perform(post(PREFIX + "{productID}/{quantity}", TEST_PRODUCT_ID, TEST_PRODUCT_QUANTITY).header("Cookie", "session=" + validSession).cookie(new Cookie("session", validSession)));
	}
	
	@Test
	public void 상품중복추가() throws Exception {
		mockMvc.perform(post(PREFIX + "{productID}/{quantity}", TEST_PRODUCT_ID, TEST_PRODUCT_QUANTITY).header("Cookie", "session=" + validSession).cookie(new Cookie("session", validSession)))
		//.andDo(print())
		.andExpect(status().isInternalServerError())
		.andExpect(jsonPath("errCode").value(608)); // SQL Insert 문제
	}
	
	@Test
	public void 없는상품추가() throws Exception {
		mockMvc.perform(post(PREFIX + "{productID}/{quantity}", -8888, TEST_PRODUCT_QUANTITY).header("Cookie", "session=" + validSession).cookie(new Cookie("session", validSession)))
		//.andDo(print())
		.andExpect(status().isInternalServerError())
		.andExpect(jsonPath("errCode").value(609)); // 없는 Product ID
	}
	
	@Test
	public void 상품조회() throws Exception {
		mockMvc.perform(get(PREFIX).header("Cookie", "session=" + validSession).cookie(new Cookie("session", validSession)))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test
	public void 상품개수증가() throws Exception {
		mockMvc.perform(put(PREFIX+"/"+TEST_PRODUCT_ID+"/plus").header("Cookie", "session=" + validSession).cookie(new Cookie("session", validSession)))
		//.andDo(print())
		.andExpect(jsonPath("msg").value("Success Inc Quantity"));
	}
	
	@Test
	public void 상품개수감소() throws Exception {
		mockMvc.perform(put(PREFIX+"/"+TEST_PRODUCT_ID+"/minus").header("Cookie", "session=" + validSession).cookie(new Cookie("session", validSession)))
		//.andDo(print())
		.andExpect(jsonPath("msg").value("Success Dec Quantity"));
	}
	
	@Test
	public void 없는상품개수증가() throws Exception {
		mockMvc.perform(put(PREFIX+"/"+(-9999)+"/plus").header("Cookie", "session=" + validSession).cookie(new Cookie("session", validSession)))
		.andExpect(status().isInternalServerError())
		.andExpect(jsonPath("errCode").value(609));
		//.andExpect(jsonPath("msg").value("Success Inc Quantity"));
	}
	
	@Test
	public void 없는상품개수감소() throws Exception {
		mockMvc.perform(put(PREFIX+"/"+(-9999)+"/minus").header("Cookie", "session=" + validSession).cookie(new Cookie("session", validSession)))
		.andExpect(status().isInternalServerError())
		.andExpect(jsonPath("errCode").value(609));
		//.andExpect(jsonPath("msg").value("Success Inc Quantity"));
	}
	
	@After
	public void mvcSetdown() throws Exception {
		mockMvc.perform(delete(PREFIX + "/{productID}", TEST_PRODUCT_ID).header("Cookie", "session=" + validSession).cookie(new Cookie("session", validSession)))
		//.andDo(print())
		.andReturn();
		mockMvc.perform(get("/user/logout").header("Cookie", "session=" + validSession))
		//.andDo(print())
		.andReturn();
		mockMvc = null;
	}
}
