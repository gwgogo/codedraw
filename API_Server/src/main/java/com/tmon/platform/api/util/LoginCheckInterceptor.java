package com.tmon.platform.api.util;

import java.net.URLDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tmon.platform.api.controller.UserController;

@Component
public class LoginCheckInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(LoginCheckInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		Cookie[] cookies = request.getCookies();
		
		
		logger.info(cookies[0].getName() + " 세션의  값 : " + cookies[0].getValue());

		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals("session")) {
				String session = URLDecoder.decode(cookies[i].getValue());
				logger.info(cookies[i].getName() + " 세션의  값 : " + session);
				return true;
			}
		}
		return false;
	}
}
