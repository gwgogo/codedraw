package com.tmon.platform.api.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		HttpSession session = request.getSession(false);
		if(session == null) {
			response.sendError(417, "Session Error");
			return false;
		}
		
		String userId = (String)session.getAttribute("user_id");
		if(userId == null) {
			response.sendError(417, "Session Error");
			return false;
		}
		return true;
	}
}
