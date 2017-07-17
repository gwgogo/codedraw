package com.tmon.platform.api.util;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class LoginAspect {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginAspect.class);
	
	@Pointcut("execution(* com.tmon.platform.api.controller.UserController.mypageData(..))")
	public void loginCheck() {
		
	}
	
	@Around("loginCheck()")
	public String parsingSession(JoinPoint joinPoint) throws Throwable {
		
		logger.info("aspect start");
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = attr.getRequest();
				
		String rawCookie = request.getHeader("Cookie");
		String[] rawCookieParams = rawCookie.split(";");
		String session = null;
		
		for(int i = 0; i < rawCookieParams.length; i++) {	
			String[] cookieParams = rawCookieParams[i].split("=");	
			if(cookieParams[0].toString().trim().equals("session")) {		
				session = cookieParams[1];
			}
		}
		
		logger.info(session);
		return session;
	}
}
