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
public class AdminCheckInterceptorTest extends HandlerInterceptorAdapter{
	private static final Logger logger = LoggerFactory.getLogger(AdminCheckInterceptor.class);
	
	@Autowired
	private SessionManager sessionManager;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		Cookie[] cookies = request.getCookies();
		String session = cookies[0].getValue();
		
		if(sessionManager.getPrivilege(session) == 1) {
			return true;
		}
		
		throw new CustomException(501, "Unauthorized");
	}
}