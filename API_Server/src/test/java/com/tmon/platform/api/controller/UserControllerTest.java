package com.tmon.platform.api.controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tmon.platform.api.dto.UserDto;
import com.tmon.platform.api.service.UserService;
import com.tmon.platform.api.util.CustomException;
import com.tmon.platform.api.util.LoginCheckInterceptor;
import com.tmon.platform.api.util.SessionManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
"file:src/test/resources/testServlet-context.xml",
"file:src/test/resources/testDataSource-context.xml" })
public class UserControllerTest {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private LoginCheckInterceptor loginCheckInterceptor;
	
	static MockHttpServletRequest request = new MockHttpServletRequest();
	static MockHttpServletResponse response = new MockHttpServletResponse();
	
	private String sessionKey;
	
	@Before
	public void before() {
		UserDto userDto = new UserDto();
		userDto.setUser_id("admin0001");
		userDto.setUser_pw("admin0001");
		userDto.setPrivilege(1);
		sessionKey = sessionManager.createSession(userDto);
		logger.info("Before - hashtable : "  + sessionManager.getSessionPool());
	}
	
	@After
	public void after() {
		logger.info("After - hashtable : "  + sessionManager.getSessionPool());
	}
	
	@Test(expected=NullPointerException.class)
	public void login() throws Exception{
		userService.login("notExistID", "password");
	}
	
	@Test(expected=SQLException.class)
	public void join() throws Exception{
		UserDto dto = new UserDto();
		dto.setUser_id("user0001");
		dto.setUser_pw("user0001");
		userService.join(dto);
	}
	
	@Test
	public void mypage() throws Exception {
		logger.info("Mypage Test");
		request.setRequestURI("/mypage");
		request.setParameter("session", sessionKey);
		Object handler = null;
		Boolean test = loginCheckInterceptor.preHandle(request, response, handler);
		assertEquals(test, true);
		
		UserDto dto = userService.user(sessionKey);
		assertThat(dto, notNullValue());
		sessionManager.deleteSession(sessionKey);
	}
	
	@Test
	public void logout() throws CustomException {
		logger.info("Logout Test");
		userService.logout(sessionKey);
	}
	
}
