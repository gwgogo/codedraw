package com.tmon.platform.api.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tmon.platform.api.dto.UserDto;
import com.tmon.platform.api.exception.AuthException;
import com.tmon.platform.api.exception.PreConditionException;
import com.tmon.platform.api.service.UserService;
import com.tmon.platform.api.util.SessionManager;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


/**
 * @author 신광원 
 * 
 */
@CrossOrigin
@RestController
@RequestMapping(value="/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SessionManager sessionManager;
	
	@ApiOperation(value="로그인", notes="로그인 성공시 API서버에서 세션키 발급 후 UI서버에서 쿠키에 저장")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userID", value = "사용자 ID", dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "userPW", value = "사용자 PW", dataType = "string", paramType = "query")
	})
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "session : 세션키"),
            @ApiResponse(code = 602, message = "Invalid User Information")
    })
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public JSONObject login(HttpServletResponse response, @RequestParam("userID") String userID, @RequestParam("userPW") String userPW) throws PreConditionException {
		
		JSONObject result = userService.login(userID, userPW);
		
		Cookie sessionCookie = new Cookie("session", (String) result.get("session"));
		sessionCookie.setPath("/");
		//sessionCookie.setDomain("52.231.28.248");
		response.addCookie(sessionCookie);
		return result;
	}
	
	
	@ApiOperation(value="로그아웃", notes="세션삭제를 위한 로그아웃")
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public JSONObject logout(HttpServletRequest request) throws AuthException {
		String rawCookie = request.getHeader("Cookie");
		String session = sessionManager.getSession(rawCookie);
		
		return userService.logout(session); 
	}
	

	@ApiOperation(value="사용자 정보 조회", notes="MyPage등에 사용할 사용자 정보 조회 - 핸들러 인터셉터에서 세션 검사")
	@RequestMapping(value="/mypage", method = RequestMethod.GET)
	public UserDto mypage(HttpServletRequest request) throws AuthException {
		String rawCookie = request.getHeader("Cookie");
		String session = sessionManager.getSession(rawCookie);		
		return userService.user(session);
	}

}
