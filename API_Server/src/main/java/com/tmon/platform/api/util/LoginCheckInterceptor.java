package com.tmon.platform.api.util;

import java.net.URLDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;



@Component
public class LoginCheckInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(LoginCheckInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		//logger.info("핸들러 인터셉터 시작");
		Cookie[] cookies = request.getCookies();
		
		for (int i = 0; i < cookies.length; i++) {
			
			if (cookies[i].getName().equals("session")) {	
				String session = cookies[i].getValue();
				response.sendRedirect("/mypageData?session=" + session);
				return true;
			}
		}
		
		response.sendRedirect("/loginForm");
		return false;
	}
}
