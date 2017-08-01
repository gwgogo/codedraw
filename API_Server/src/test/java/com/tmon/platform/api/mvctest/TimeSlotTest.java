package com.tmon.platform.api.mvctest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class TimeSlotTest extends PlatformMVCTest {

	private String user_id;
	private String user_pw;

	@Before
	public void mvcSetup() {
		logger.info("MVC Setup");
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

		this.user_id = "admin0001";
		this.user_pw = "admin0001";
	}

	@Test
	public void test() throws Exception {

		JSONParser parser = new JSONParser();
		String responseBodystr = null;
		JSONObject responseBody = null;

		logger.info("MVC test example");

		MvcResult response = mockMvc.perform(post("/user/login").param("user_id", user_id).param("user_pw", user_pw))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8)).andReturn();

		responseBodystr = response.getResponse().getContentAsString();
		responseBody = (JSONObject) parser.parse(responseBodystr);

		String cookie = "Cookie=aaa;session=" + responseBody.get("session").toString();
		MvcResult response_holidays = mockMvc.perform(get("/holidays").header("Cookie", cookie)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8)).andReturn();

		responseBodystr = response_holidays.getResponse().getContentAsString();
		responseBody = (JSONObject) parser.parse(responseBodystr);

		logger.info(responseBody.toJSONString());

	}

}
