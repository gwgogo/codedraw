package com.tmon.platform.api.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import javax.servlet.http.Cookie;

import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.tmon.platform.api.dto.UserDto;
import com.tmon.platform.api.exception.AuthException;
import com.tmon.platform.api.exception.PreConditionException;
import com.tmon.platform.api.exception.SQLCustomException;
import com.tmon.platform.api.interceptor.AdminCheckInterceptorTest;
import com.tmon.platform.api.interceptor.LoginCheckInterceptorTest;
import com.tmon.platform.api.service.UserService;
import com.tmon.platform.api.util.SessionManager;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
"file:src/test/resources/testServlet-context.xml",
"file:src/test/resources/testDataSource-context.xml",
"file:src/test/resources/testDateFormat-context.xml"})
public class UserServiceTest {
private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private LoginCheckInterceptorTest loginCheckInterceptorTest;
	
	@Autowired
	private AdminCheckInterceptorTest adminCheckInterceptorTest;
	
	
	private static final Object HANDLER = null;
	private static final MockHttpServletRequest REQUEST = new MockHttpServletRequest();
	private static final MockHttpServletResponse RESPONSE = new MockHttpServletResponse();
	private static final String USER_ID = "user0001";
	private static final String USER_PW = "user0001";
	private static final String ADMIN_ID = "admin0001";
	private static final String ADMIN_PW = "admin0001";
	private String session;
	
	
	@Before
	public void before()  {
		
	}
	
	@After
	public void after() {
		userService.logout(session);
	}
	
	@Test
	public void loginTest() throws PreConditionException {
		JSONObject obj = userService.login(USER_ID, USER_PW);
		session = obj.get("session").toString();
		
		/* 세션 풀에 세션이 삽입됐는지 체크*/
		assertTrue(sessionManager.isValidSession(session));
		Cookie cookies = new Cookie("session", session);
		REQUEST.setCookies(cookies);
		UserDto userDto = userService.user(session);
		assertEquals(userDto.getUserID(), USER_ID);
		
	}
	
	
	@Test
	public void loginInterceptorTest() throws AuthException, PreConditionException {
		loginTest();
		Boolean handlerTest = loginCheckInterceptorTest.preHandle(REQUEST, RESPONSE, HANDLER);		
		assertEquals(handlerTest, true);
	}
	
	
	@Test(expected=SQLCustomException.class)
	public void join() throws SQLCustomException{
		UserDto dto = new UserDto();
		dto.setUserID("user0001");
		dto.setUserPW("user0001");
		userService.join(dto);
	}
	

	@Test
	public void mypage() throws AuthException, PreConditionException {
		loginTest();
		loginInterceptorTest();
		Cookie cookie = new Cookie("session", session);	// 테스트 할 때는 직접 쿠키에 넣어서 핸들러 인터셉터로 전송
		REQUEST.setCookies(cookie);
		Boolean test = loginCheckInterceptorTest.preHandle(REQUEST, RESPONSE, HANDLER);
		
		assertEquals(test, true);
		UserDto dto = userService.user(session);
		assertThat(dto.getUserID(), is(USER_ID));
		assertThat(dto, notNullValue());
		sessionManager.deleteSession(session);
	}
	
	
	@Test
	public void admin() throws AuthException, PreConditionException {
		session = userService.login(ADMIN_ID, ADMIN_PW).get("session").toString();
		
		Cookie cookie = new Cookie("session", session);	// 테스트 할 때는 직접 쿠키에 넣어서 핸들러 인터셉터로 전송
		REQUEST.setCookies(cookie);
		
		Boolean test = adminCheckInterceptorTest.preHandle(REQUEST, RESPONSE, HANDLER);
		assertEquals(test,true);
		UserDto dto = userService.user(session);
		assertThat(dto.getUserID(), is(ADMIN_ID));
		
	}
	
	@Test
	public void logout() throws PreConditionException {
		loginTest();
		userService.logout(session);
		assertNull(sessionManager.getUserId(session));
	}
	
}
