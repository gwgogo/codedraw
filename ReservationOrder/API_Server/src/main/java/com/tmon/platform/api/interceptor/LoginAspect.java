package com.tmon.platform.api.interceptor;

import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoginAspect {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginAspect.class);
	
	/*@Pointcut("execution(* com.tmon.platform.api.controller.UserController.mypageData(..))")
	public void loginCheck() {
		
	}
	
	@Around ("loginCheck()")
	public Object parsingSession(ProceedingJoinPoint joinPoint) throws Throwable {
		
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
		
		Object resultObject = joinPoint.proceed(new Object[] {session});
		
		return resultObject;
	}*/
}
