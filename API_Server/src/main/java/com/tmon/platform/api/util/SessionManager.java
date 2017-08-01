package com.tmon.platform.api.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.tmon.platform.api.dto.UserDto;
import com.tmon.platform.api.exception.NullCustomException;

/**
 * @since 2017-07-13
 * @author gwlee
 * @version 1
 * 
 * - UserDto 를 활용하여 세션값을 생성합니다.
 * - 세션값을 복호화해 세션값의 원래 아이디, 권한을 가져옵니다.
 * - 세션 pool을 활용하여 validation을 실행합니다. expire 생각하지 않습니다.
 */
@Component
public class SessionManager {
	private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);
	Hashtable<String, UserDto> sessionPool = new Hashtable<>();
	
	/**
	 * @author gwlee
	 * @param userDto 등록할 유저정보
	 * @return 세션키값
	 * 세션을 등록한다.
	 * 세션키값은 MD5로, 유저명으로 만든다. 복호화하여 사용하지 않는다.
	 * 이미 존재하는 세션인지 확인하지 않는다.
	 * 세션의 만료시간을 생각하지 않는다.
	 * 같은 유저의 경우 같은 세션값이 부여된다.
	 */
	public String createSession(UserDto userDto){
		
		resetSession(userDto);	// sessionPool에 있는 사용자가 새로운 로그인 시도하면 기존 세션 삭제
		
		String sessionKey = null;
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			String originalString = userDto.getUserID().toString() + "TMON"; // salting
			md.update(originalString.getBytes());
			
			sessionKey = md.digest().toString();
			sessionPool.put(sessionKey, userDto);

		} catch(NoSuchAlgorithmException e) {
			logger.error("SessionManager.getSessionKey(NoSuchAlgorithmException) : " +
					userDto.getUserID(), e);
		}
		
		return sessionKey;
	}
	
	/* 접속했던 사용자가 로그아웃하지 않고 새로운 접속을 시도할 때 기존 세션을 풀에서 삭제하고 새로운 세션 발급 */ 
	private void resetSession(UserDto userDto) {
		for(Map.Entry<String, UserDto> entry : sessionPool.entrySet()) {
			if(entry.getValue().getUserID().equals(userDto.getUserID())) {
				String session = entry.getKey();
				sessionPool.remove(session);
			}
		}
	}
	
	/**
	 * @author gwlee
	 * @param session 세션키값
	 * @return 정상적인 세션인 경우 해당 유저의 권한, 아닌 경우 -1
	 */
	public int getPrivilege(String session) {
		UserDto user = getValidUserDto(session);
		if(user != null) {
			return user.getPrivilege();
		} else {
			return -1;
		}
	}
	
	public String getUserId(String session) {
		UserDto user = getValidUserDto(session);
		if(user != null) {
			return user.getUserID(); 
		} else {
			return null;
		}
	}
	
	public UserDto getValidUserDto(String session) {
		if(!sessionPool.containsKey(session))
			return null;
		else 
			return sessionPool.get(session);
	}
	
	public void deleteSession(String session) {
		if(sessionPool.containsKey(session)) {
			sessionPool.remove(session);
		}
	}
	
	public boolean isValidSession(String session) {
		if(sessionPool.containsKey(session)) {
			return true;
		}
		return false;
	}
	
	public String getSession(String rawCookie) throws NullCustomException {
		if(rawCookie == null) {
			throw new NullCustomException(619, "rawCookie is null");
		}
		String session = null;
		try {
			rawCookie = URLDecoder.decode(rawCookie, "UTF-8");
			String[] rawCookieParams = rawCookie.split(";");
			
			for(int i = 0; i < rawCookieParams.length; i++) {	
				String[] cookieParams = rawCookieParams[i].split("=");	
				if(cookieParams[0].toString().trim().equals("session")) {		
					session = cookieParams[1];
					session = session.replaceAll("^\"|\"$", "");
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return session;
	}
}
