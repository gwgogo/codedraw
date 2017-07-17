package com.tmon.platform.api.util;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@CrossOrigin
@Component
public class LoginCheckInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(LoginCheckInterceptor.class);

	@Autowired
	private SessionManager sessionManager;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		
		String rawCookie = request.getHeader("Cookie");
		String[] rawCookieParams = rawCookie.split(";");
		String[] cookieParams = null;
		String session = null;
		
		for(int i = 0; i < rawCookieParams.length; i++) {	
			cookieParams = rawCookieParams[i].split("=");	
			if(cookieParams[0].toString().trim().equals("session")) {		// cookie의 key가 "session" 일 때
				session = cookieParams[1];									// key에 해당하는 value를 가져오고
				logger.info("session : " + session );
				
				if(sessionManager.getSessionPool().containsKey(session)) {	// 그 value(세션키)가 현재 세션풀에 있는지 검사하여 있으면 return true
					return true;
				}
			}
		}
		
		logger.info("핸들러 인터셉터 종료");
		throw new CustomException(501, "Login HandlerInterceptor false");
		
		//return false;
	}
}
