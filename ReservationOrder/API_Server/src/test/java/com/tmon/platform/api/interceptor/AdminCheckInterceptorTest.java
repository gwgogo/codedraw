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
public class AdminCheckInterceptorTest extends HandlerInterceptorAdapter {

	@Autowired
	private SessionManager sessionManager;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws AuthException {

		Cookie[] cookies = request.getCookies();
		String session = cookies[0].getValue();

		if (sessionManager.getPrivilege(session) == 1) {
			return true;
		}

		throw new AuthException(606, "Admin Unauthorized");
	}
}
