package com.tmon.platform.api.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmon.platform.api.dto.UserDto;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.exception.PreConditionException;
import com.tmon.platform.api.exception.SQLCustomException;
import com.tmon.platform.api.service.UserService;
import com.tmon.platform.api.util.SessionManager;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;



@CrossOrigin
@Controller
@RequestMapping(value="/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SessionManager sessionManager;
	
	@RequestMapping(value="/main", method = RequestMethod.GET)
	public String main() {
		return "main";
	}
	
	@RequestMapping(value="/mypageForm", method = RequestMethod.GET)
	public String mypage() {
		return "mypageForm";
	}
	
	@RequestMapping(value="/adminForm", method = RequestMethod.GET)
	public String adminForm() {
		return "adminForm";
	}
	
	@ApiOperation(value = "로그인 폼")
	@RequestMapping(value = "/loginForm", method = RequestMethod.GET)
	public String loginForm() {
		return "loginForm";
	}

	@ApiOperation(value = "회원가입 폼")
	@RequestMapping(value = "/joinForm", method = RequestMethod.GET)
	public String joinForm() {
		return "joinForm";
	}

	
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
	@ResponseBody
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
	@ResponseBody
	public JSONObject logout(HttpServletRequest request) throws NullCustomException {
		String rawCookie = request.getHeader("Cookie");
		String session = sessionManager.getSession(rawCookie);
		
		return userService.logout(session);
	}

	
	@ApiOperation(value="회원가입", notes="현재는 ID, PW 만으로 회원가입 가능")
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Join Success"),
            @ApiResponse(code = 603, message = "Fail : User Join SQL Error")
    })
	@ResponseBody
	public JSONObject join(@RequestParam("userID") String userID, @RequestParam("userPW") String userPW) throws SQLCustomException  {
		UserDto userDto = new UserDto();
		userDto.setUserID(userID);
		userDto.setUserPW(userPW);
		return userService.join(userDto);
	}
	

	@ApiOperation(value="사용자 정보 조회", notes="MyPage등에 사용할 사용자 정보 조회 - 핸들러 인터셉터에서 세션 검사")
	@RequestMapping(value="/mypage", method = RequestMethod.GET)
	@ResponseBody
	public UserDto mypage(HttpServletRequest request) throws NullCustomException {
		logger.info("mypage start");
		String rawCookie = request.getHeader("Cookie");
		String session = sessionManager.getSession(rawCookie);
		
		return userService.user(session);
	}
	
	
	@RequestMapping(value="/admin", method = RequestMethod.GET)
	@ResponseBody
	public UserDto admin(HttpServletRequest request) throws NullCustomException {
		logger.info("admin start");
		String rawCookie = request.getHeader("Cookie");
		String session = sessionManager.getSession(rawCookie);
		
		return userService.user(session);
	}
	
}
