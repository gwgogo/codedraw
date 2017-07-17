package com.tmon.platform.api.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

<<<<<<< HEAD


=======
@CrossOrigin
>>>>>>> cb9bf97e921d0ba126db26909b9d8211bab8c3db
@Component
public class LoginCheckInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(LoginCheckInterceptor.class);

	@Autowired
	private SessionManager sessionManager;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

<<<<<<< HEAD
		//logger.info("핸들러 인터셉터 시작");
		Cookie[] cookies = request.getCookies();
		
		for (int i = 0; i < cookies.length; i++) {
			
			if (cookies[i].getName().equals("session")) {	
				String session = cookies[i].getValue();
				response.sendRedirect("/mypageData?session=" + session);
				return true;
			}
		}
		
=======
		
		String rawCookie = request.getHeader("Cookie");
		String[] rawCookieParams = rawCookie.split(";");
		String[] cookieParams = null;
		String session = null;
		
		for(int i = 0; i < rawCookieParams.length; i++) {	
			cookieParams = rawCookieParams[i].split("=");	
			if(cookieParams[i].equals("session")) {				// cookie의 key가 "session" 일 때
				session = cookieParams[i+1];					// key에 해당하는 value를 가져오고
				logger.info("session : " + session );
				
				if(sessionManager.getSessionPool().containsKey(session)) {	// 그 value(세션키)가 현재 세션풀에 있는지 검사하여 있으면 return true
					return true;
				}
			}
		}
		
		logger.info("핸들러 인터셉터 종료");
>>>>>>> cb9bf97e921d0ba126db26909b9d8211bab8c3db
		response.sendRedirect("/loginForm");
		return false;
	}
}
