package com.tmon.platform.api.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import javax.servlet.http.Cookie;

import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.tmon.platform.api.dto.UserDto;
import com.tmon.platform.api.exception.AuthException;
import com.tmon.platform.api.exception.PreConditionException;
import com.tmon.platform.api.interceptor.AdminCheckInterceptorTest;
import com.tmon.platform.api.interceptor.LoginCheckInterceptorTest;
import com.tmon.platform.api.util.SessionManager;



/**
 * @author 신광원
 * @description 로그인 및 로그아웃, 인터셉터 등에 대한 TestCode
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
"file:src/test/resources/testServlet-context.xml",
"file:src/test/resources/testDataSource-context.xml"})
public class UserServiceTest {

	
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
	private static final String ADMIN_ID = "admin0002";
	private static final String ADMIN_PW = "admin0002";
	private String session;
	
	
	@Test
	public void 유효하지않은사용자의로그인() throws PreConditionException {
		JSONObject obj = null;
		try {
			obj = userService.login("invalidUser", "invalidPassword");
		}catch(PreConditionException e) {
			assertNull(obj);
		}
		
	}
	
	@Test
	public void 유효한사용자의로그인() throws PreConditionException {
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
	public void 로그인체크인터셉터() throws AuthException, PreConditionException {
		유효한사용자의로그인();
		Boolean handlerTest = loginCheckInterceptorTest.preHandle(REQUEST, RESPONSE, HANDLER);		
		assertEquals(handlerTest, true);
		userService.logout(session);
		assertFalse(sessionManager.isValidSession(session));
	}
	

	@Test(expected=AuthException.class)
	public void 유효하지않은로그인체크인터셉터() throws AuthException, PreConditionException {
		Cookie cookies = new Cookie("session", "invalidSession");
		REQUEST.setCookies(cookies);
		Boolean handlerTest = loginCheckInterceptorTest.preHandle(REQUEST, RESPONSE, HANDLER);		
		assertEquals(handlerTest, true);
	}
	
	
	@Test
	public void mypage() throws AuthException, PreConditionException {
		유효한사용자의로그인();
		Boolean test = loginCheckInterceptorTest.preHandle(REQUEST, RESPONSE, HANDLER);
		
		assertEquals(test, true);
		UserDto dto = userService.user(session);
		assertThat(dto.getUserID(), is(USER_ID));
		assertThat(dto, notNullValue());
		
		userService.logout(session);
		assertFalse(sessionManager.isValidSession(session));
	}
	
	
	@Test
	public void 유효한관리자테스트() throws AuthException, PreConditionException {
		String adminSession = userService.login(ADMIN_ID, ADMIN_PW).get("session").toString();
		assertTrue(sessionManager.isValidSession(adminSession));
		Cookie cookie = new Cookie("session", adminSession);	// 테스트 할 때는 직접 쿠키에 넣어서 핸들러 인터셉터로 전송
		REQUEST.setCookies(cookie);
		
		Boolean test = adminCheckInterceptorTest.preHandle(REQUEST, RESPONSE, HANDLER);
		assertEquals(test,true);
		UserDto dto = userService.user(adminSession);
		assertThat(dto.getUserID(), is(ADMIN_ID));
		
		userService.logout(adminSession);
	}
	
	@Test(expected=AuthException.class)
	public void 유효하지않은관리자테스트() throws AuthException, PreConditionException {
		String adminSession = userService.login(USER_ID, USER_PW).get("session").toString();
		assertTrue(sessionManager.isValidSession(adminSession));
		Cookie cookie = new Cookie("session", adminSession);	// 테스트 할 때는 직접 쿠키에 넣어서 핸들러 인터셉터로 전송
		REQUEST.setCookies(cookie);
		
		adminCheckInterceptorTest.preHandle(REQUEST, RESPONSE, HANDLER);
	}
	
	@Test
	public void logout() throws PreConditionException {
		유효한사용자의로그인();
		assertNotNull(sessionManager.getUserId(session));
		userService.logout(session);
		assertNull(sessionManager.getUserId(session));
	}
	
}
