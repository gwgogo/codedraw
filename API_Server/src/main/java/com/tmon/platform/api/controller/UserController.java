package com.tmon.platform.api.controller;

import javax.servlet.http.HttpServletRequest;

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
import com.tmon.platform.api.exception.CustomException;
import com.tmon.platform.api.service.UserService;
import com.tmon.platform.api.util.SessionManager;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;



@CrossOrigin
@Controller
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
	
	@RequestMapping(value="/mypage", method = RequestMethod.GET)
	public String mypage() {
		return "mypage";
	}
	
	@RequestMapping(value="/admin", method = RequestMethod.GET)
	public String admin() {
		return "admin";
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

	
	/**
	 * @author gwlee
	 * @since 2017-07-13
	 */
	@ApiOperation(value="로그인", notes="로그인 성공시 API서버에서 세션키 발급 후 UI서버에서 쿠키에 저장")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user_id", value = "사용자 ID", dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "user_pw", value = "사용자 PW", dataType = "string", paramType = "query")
	})
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "{session : 세션키}"),
            @ApiResponse(code = 501, message = "{msg : Invalid User Information}")
    })
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject login(@RequestParam("user_id") String user_id, @RequestParam("user_pw") String user_pw) throws CustomException {
		return userService.login(user_id, user_pw);
	}
	
	
	@ApiOperation(value="로그아웃", notes="세션삭제를 위한 로그아웃")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "{msg : Success Delete Session}"),
            @ApiResponse(code = 501, message = "{msg : Invalid Session}")
    })
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject logout(HttpServletRequest request) throws CustomException {
		String rawCookie = request.getHeader("Cookie");
		String session = sessionManager.getSession(rawCookie);
		
		return userService.logout(session);
	}

	
	@ApiOperation(value="회원가입", notes="현재는 ID, PW 만으로 회원가입 가능")
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "{msg : Join Success}"),
            @ApiResponse(code = 501, message = "{msg : Join Error(duplication ID)}")
    })
	@ResponseBody
	public JSONObject join(@RequestParam("user_id") String user_id, @RequestParam("user_pw") String user_pw) throws Exception {
		UserDto userDto = new UserDto();
		userDto.setUser_id(user_id);
		userDto.setUser_pw(user_pw);
		return userService.join(userDto);
	}
	

	@ApiOperation(value="사용자 정보 조회", notes="MyPage등에 사용할 사용자 정보 조회 - 핸들러 인터셉터에서 세션 검사")
	@RequestMapping(value="/mypageData", method = RequestMethod.GET)
	@ResponseBody
	public UserDto mypageData(HttpServletRequest request) {
		
		String rawCookie = request.getHeader("Cookie");
		String session = sessionManager.getSession(rawCookie);
		
		return userService.user(session);
	}
