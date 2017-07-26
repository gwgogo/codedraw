package com.tmon.platform.api.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tmon.platform.api.exception.AuthException;
import com.tmon.platform.api.util.SessionManager;

public class AdminCheckInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(AdminCheckInterceptor.class);

	@Autowired
	private SessionManager sessionManager;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws AuthException {

		String rawCookie = request.getHeader("Cookie");
		String session = sessionManager.getSession(rawCookie);
		if (sessionManager.getPrivilege(session) == 1) {
			return true;
		}

		//throw new AuthException(606, "Admin Unauthorized");
		return false;
	}

}
