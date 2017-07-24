package com.tmon.platform.api.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import javax.servlet.http.Cookie;

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
import org.springframework.test.context.web.WebAppConfiguration;

import com.tmon.platform.api.dao.UserDao;
import com.tmon.platform.api.dto.UserDto;
import com.tmon.platform.api.exception.CustomException;
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
public class UserControllerTest {
private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private LoginCheckInterceptorTest loginCheckInterceptorTest;
	
	@Autowired
	private AdminCheckInterceptorTest adminCheckInterceptorTest;
	
	
	static MockHttpServletRequest request = new MockHttpServletRequest();
	static MockHttpServletResponse response = new MockHttpServletResponse();
	
	private String sessionKey;
	
	@Before
	public void before() {
		UserDto userDto = userDao.user("admin0001");
		sessionKey = sessionManager.createSession(userDto);
	}
	
	@After
	public void after() {
		sessionManager.deleteSession(sessionKey);
	}
	
	@Test(expected=CustomException.class)
	public void login() throws Exception{
		userService.login("notExistID", "password");
	}
	
	@Test(expected=CustomException.class)
	public void join() throws Exception{
		UserDto dto = new UserDto();
		dto.setUser_id("user0001");
		dto.setUser_pw("user0001");
		userService.join(dto);
	}
	

	@Test
	public void mypage() throws Exception {
		logger.info("Mypage Test");
		
		Cookie cookie = new Cookie("session", sessionKey);	// 테스트 할 때는 직접 쿠키에 넣어서 핸들러 인터셉터로 전송
		request.setCookies(cookie);
		Object handler = null;
		Boolean test = loginCheckInterceptorTest.preHandle(request, response, handler);
		
		assertEquals(test, true);
		UserDto dto = userService.user(sessionKey);
		assertThat(dto.getUser_id(), is("user0001"));
		assertThat(dto, notNullValue());
		sessionManager.deleteSession(sessionKey);
	}
	
	
	@Test
	public void admin() throws Exception {
		Cookie cookie = new Cookie("session", sessionKey);	// 테스트 할 때는 직접 쿠키에 넣어서 핸들러 인터셉터로 전송
		request.setCookies(cookie);
		Object handler = null;
		
		Boolean test = adminCheckInterceptorTest.preHandle(request, response, handler);
		assertEquals(test,true);
		UserDto dto = userService.user(sessionKey);
		assertThat(dto.getUser_id(), is("admin0001"));
		sessionManager.deleteSession(sessionKey);
	}
	
	@Test
	public void logout() throws CustomException {
		logger.info("Logout Test");
		userService.logout(sessionKey);
	}
	
}
