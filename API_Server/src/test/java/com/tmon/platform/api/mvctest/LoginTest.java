package com.tmon.platform.api.mvctest;

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

public class LoginTest extends PlatformMVCTest {

	@Before
	public void mvcSetup() {
		logger.info("MVC Setup");
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void test() throws Exception {
		logger.info("MVC test example");

		String user_id = "admin0001";
		String user_pw = "admin0001";

		MvcResult response = mockMvc.perform(post("/user/login").param("user_id", user_id).param("user_pw", user_pw))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8)).andReturn();

		String responseBodstr = response.getResponse().getContentAsString();
		JSONParser parser = new JSONParser();
		JSONObject responseBody = (JSONObject) parser.parse(responseBodstr);
		logger.info(responseBody.get("session").toString());
	}
}
