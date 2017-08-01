package com.tmon.platform.api.interceptor;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tmon.platform.api.exception.AuthException;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.util.SessionManager;

@Component
public class LoginCheckInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(LoginCheckInterceptor.class);

	@Autowired
	private SessionManager sessionManager;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws AuthException, NullCustomException {
		 
		
		// POST method preflight Request시 인터셉터 로직을 거치지 않게하기 위함
		if (request.getMethod().equals("OPTIONS")) {
			return true;
		}
		/*
		 * Cookie[] cookies = request.getCookies(); String session = null; for(int idx =
		 * 0; idx < cookies.length; idx++) {
		 * if(cookies[idx].getName().equals("session")) { session =
		 * cookies[idx].getValue(); } }
		 */
		String rawCookie = request.getHeader("Cookie");
		String session = sessionManager.getSession(rawCookie);

		if (sessionManager.isValidSession(session)) { // 그 value(세션키)가 현재 세션풀에 있는지 검사하여 있으면 return true
			return true;
		}

		throw new AuthException(606, "Login Unauthorized");
	}
}
