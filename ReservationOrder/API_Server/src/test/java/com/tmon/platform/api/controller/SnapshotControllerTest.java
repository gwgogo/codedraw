package com.tmon.platform.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import javax.servlet.http.Cookie;

import org.json.simple.JSONObject;
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
 * 스냅샷에 한정, Date format은 yyyy-MM-dd HH:mm:ss 입니다.
 * @author gwlee
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
"file:src/test/resources/testServlet-context.xml",
"file:src/test/resources/testDataSource-context.xml"})
public class SnapshotControllerTest {
	@Autowired
	public WebApplicationContext wac;
	public MockMvc mockMvc;
	public String validSession;
	private static final String PREFIX = "/snapshots";
	private static final String USER_ID = "user0001";
	private static final String USER_PW = "user0001";
	private static final String ADMIN_ID = "admin0001";
	private static final String ADMIN_PW = "admin0001";
	
	private void 로그인(String id, String pw) throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		MvcResult response =
				mockMvc.perform(post("/user/login").param("userID", id).param("userPW", pw))
				//.andDo(print())
				.andReturn();
		
		String responseBodstr = response.getResponse().getContentAsString();
		JSONParser parser = new JSONParser();
		JSONObject responseBody = (JSONObject) parser.parse(responseBodstr);
		validSession = responseBody.get("session").toString();
	}
	
	private void 로그아웃() throws Exception {
		mockMvc.perform(get("/user/logout").header("Cookie", "session=" + validSession));
		mockMvc = null;
		validSession = null;
	}
	
	@Test
	public void 어드민_정상조회() throws Exception {
		로그인(ADMIN_ID,ADMIN_PW);
		mockMvc.perform(get(PREFIX)
				.param("searchInitTime", "2017-07-01 00:00:00").param("searchFinishTime", "2017-07-31 23:59:59")
				.header("Cookie", "session=" + validSession).cookie(new Cookie("session", validSession)))
		.andDo(print());
		로그아웃();
	}
	
	@Test
	public void 어드민_잘못된포맷입력() throws Exception {
		로그인(ADMIN_ID,ADMIN_PW);
		mockMvc.perform(get(PREFIX)
				.param("searchInitTime", "2017-07-01").param("searchFinishTime", "2017-07-31")
				.header("Cookie", "session=" + validSession).cookie(new Cookie("session", validSession)))
		.andDo(print())
		.andExpect(jsonPath("errCode").value(616));
		로그아웃();
	}
	
	@Test
	public void 어드민_잘못된포맷입력2() throws Exception {
		로그인(ADMIN_ID,ADMIN_PW);
		mockMvc.perform(get(PREFIX)
				.param("searchInitTime", "00:00:00").param("searchFinishTime", "23:59:59")
				.header("Cookie", "session=" + validSession).cookie(new Cookie("session", validSession)))
		.andDo(print())
		.andExpect(jsonPath("errCode").value(616));
		로그아웃();
	}
	
	@Test
	public void 어드민_앞뒤바꿔서입력() throws Exception {
		로그인(ADMIN_ID,ADMIN_PW);
		mockMvc.perform(get(PREFIX)
				.param("searchInitTime", "2017-07-31 23:59:59").param("searchFinishTime", "2017-07-01 00:00:00")
				.header("Cookie", "session=" + validSession).cookie(new Cookie("session", validSession)))
		.andDo(print())
		.andExpect(jsonPath("errCode").value(616));
		로그아웃();
	}
	
	@Test
	public void 유저_정상조회() throws Exception {
		로그인(USER_ID,USER_PW);
		mockMvc.perform(get(PREFIX)
				.param("searchInitTime", "2017-07-01 00:00:00").param("searchFinishTime", "2017-07-31 23:59:59")
				.header("Cookie", "session=" + validSession).cookie(new Cookie("session", validSession)))
		.andDo(print())
		.andExpect(jsonPath("errCode").value(607));
		로그아웃();
	}
	
	@Test
	public void 유저_잘못된포맷입력() throws Exception {
		로그인(USER_ID,USER_PW);
		mockMvc.perform(get(PREFIX)
				.param("searchInitTime", "2017-07-01").param("searchFinishTime", "2017-07-31")
				.header("Cookie", "session=" + validSession).cookie(new Cookie("session", validSession)))
		.andDo(print())
		.andExpect(jsonPath("errCode").value(607));
		로그아웃();
	}
	
	@Test
	public void 유저_잘못된포맷입력2() throws Exception {
		로그인(USER_ID,USER_PW);
		mockMvc.perform(get(PREFIX)
				.param("searchInitTime", "00:00:00").param("searchFinishTime", "23:59:59")
				.header("Cookie", "session=" + validSession).cookie(new Cookie("session", validSession)))
		.andDo(print())
		.andExpect(jsonPath("errCode").value(607));
		로그아웃();
	}
	
	@Test
	public void 유저_앞뒤바꿔서입력() throws Exception {
		로그인(USER_ID,USER_PW);
		mockMvc.perform(get(PREFIX)
				.param("searchInitTime", "2017-07-31 23:59:59").param("searchFinishTime", "2017-07-01 00:00:00")
				.header("Cookie", "session=" + validSession).cookie(new Cookie("session", validSession)))
		.andDo(print())
		.andExpect(jsonPath("errCode").value(607));
		로그아웃();
	}
	
	@Test
	public void 로그인안하고_정상조회() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		mockMvc.perform(get(PREFIX)
				.param("searchInitTime", "2017-07-01 00:00:00").param("searchFinishTime", "2017-07-31 23:59:59")
				.header("Cookie", "session=" + validSession).cookie(new Cookie("session", validSession)))
		.andDo(print())
		.andExpect(jsonPath("errCode").value(607));
	}
}
