package com.tmon.platform.api.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		try {
			if(request.getSession().getAttribute("user_id") == null){
				response.sendRedirect("/loginForm");
				return false;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return true;
		//return super.preHandle(request, response, handler);
	}
	

}
