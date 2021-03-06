package com.tmon.platform.api.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tmon.platform.api.exception.AuthException;
import com.tmon.platform.api.util.SessionManager;

@Component
public class LoginCheckInterceptorTest extends HandlerInterceptorAdapter {

	@Autowired
	private SessionManager sessionManager;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws AuthException {
		Cookie[] cookie =request.getCookies();
		String session = cookie[0].getValue(); 
		
		if(!sessionManager.isValidSession(session)) {	// 세션키가 현재 세션풀에 있는지 검사하여 있으면 return true
			throw new AuthException(606, "Login Unauthorized");
		}
		return true;
	}
}
