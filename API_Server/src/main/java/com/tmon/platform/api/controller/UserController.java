package com.tmon.platform.api.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmon.platform.api.dto.UserDto;
import com.tmon.platform.api.service.UserService;
import com.tmon.platform.api.util.CustomException;

import io.swagger.annotations.ApiOperation;

@Controller
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	
	@RequestMapping(value="/main", method = RequestMethod.GET)
	public String main() {
		return "main";
	}
	
	@RequestMapping(value="/mypage", method = RequestMethod.GET)
	public String mypage() {
		return "mypage";
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
	
	@ApiOperation(value = "사용자 리스트")
	@RequestMapping(value="/user", method=RequestMethod.GET)
	@ResponseBody
	public List<UserDto> user(){
		return userService.user();
	}
	
	/**
	 * @author gwlee
	 * @since 2017-07-13
	 * @param user_id 유저아이디
	 * @param user_pw 유저비밀번호
	 * @return success : {session : 세션키} / fail : CustomException
	 * @throws CustomException {msg : Invalid User Information}
	 */
	@ApiOperation(value = "로그인")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject login(@RequestParam("user_id") String user_id, @RequestParam("user_pw") String user_pw) throws CustomException {
		return userService.login(user_id, user_pw);
	}

	
	@ApiOperation(value = "회원가입")
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	@ResponseBody
	public String join(@RequestParam("user_id") String user_id, @RequestParam("user_pw") String user_pw) throws Exception {
		UserDto userDto = new UserDto();
		userDto.setUser_id(user_id);
		userDto.setUser_pw(user_pw);
		return userService.join(userDto);
	}
	
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public String ExceptionHandler(Exception e) {
		logger.error("Error Message : " + e.getMessage());			
		return e.getMessage();
	}
	

}
