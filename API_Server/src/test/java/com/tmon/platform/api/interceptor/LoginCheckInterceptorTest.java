package com.tmon.platform.api.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tmon.platform.api.exception.CustomException;
import com.tmon.platform.api.util.SessionManager;

@Component
public class LoginCheckInterceptorTest extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(LoginCheckInterceptor.class);

	@Autowired
	private SessionManager sessionManager;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		logger.info("LoginCheckInterceptorTest Strart!!");
		
		Cookie[] cookie =request.getCookies();
		String session = cookie[0].getValue(); 
		
		if(sessionManager.getValidUserDto(session) == null) {	// 그 value(세션키)가 현재 세션풀에 있는지 검사하여 있으면 return true
			throw new CustomException(501, "Login HandlerInterceptor false");
		}
		return true;
	}
}
