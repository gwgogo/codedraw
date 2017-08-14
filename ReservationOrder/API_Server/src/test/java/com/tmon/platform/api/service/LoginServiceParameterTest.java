package com.tmon.platform.api.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tmon.platform.api.exception.PreConditionException;

@RunWith(Parameterized.class)
public class LoginServiceParameterTest {
	public static UserService userService;
	
	@BeforeClass
	public static void setup() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{
				"file:src/test/resources/testServlet-context.xml",
				"file:src/test/resources/testDataSource-context.xml"});
		userService = ctx.getBean(UserService.class);
	}

	@Parameters
	public static Collection params() {
		return Arrays.asList(new Object[][] {
			{"admin0001", "admin0001", true},{"user0001","user0001", true},
			{"user0001", "123456", false}, {"user0001", "' or 1=1;--", false},
			{"user0001", "\" or 1=1;--", false}, {"user0001", "; or 1=1;--", false},
			});
	}
	
	@Parameter(0)
	public String user;
	
	@Parameter(1)
	public String password;
	
	@Parameter(2)
	public boolean valid;
	
	JSONObject loginResult;
	
	@After
	public void 로그아웃() {
		if(loginResult != null)
			userService.logout((String) loginResult.get("session"));
	}
	
	@Test
	public void 로그인파라미터테스트() {
		try {
			loginResult = userService.login(user, password);
			assertTrue(valid);
		} catch (PreConditionException e) {
			assertFalse(valid);
		}
	}
	
	
}
