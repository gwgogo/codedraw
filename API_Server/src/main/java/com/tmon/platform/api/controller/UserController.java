package com.tmon.platform.api.controller;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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

	/*@ApiOperation(value = "로그인")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject login(@RequestParam("user_id") String user_id, @RequestParam("user_pw") String user_pw) throws CustomException {
		
		return userService.login(user_id, user_pw);
	}*/
	
	
	
	@ApiOperation(value = "로그인")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam("user_id") String user_id, @RequestParam("user_pw") String user_pw, HttpSession session, ModelMap map) {
		String returnURL="";
		if(userService.login(user_id, user_pw)) {
			map.addAttribute("user_id", user_id);
			session.setAttribute("user_id", user_id);
			returnURL = "redirect:/main";
		}else {
			returnURL = "redirect:/loginForm";
		}
		return returnURL;
	}

	@ApiOperation(value = "회원가입")
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject join(@RequestParam("user_id") String user_id, @RequestParam("user_pw") String user_pw) throws CustomException {
		UserDto userDto = new UserDto();
		userDto.setUser_id(user_id);
		userDto.setUser_pw(user_pw);
		return userService.join(userDto);
	}
	
	
	@ExceptionHandler(CustomException.class)
	@ResponseBody
	public String CustomExceptionHandler(CustomException e) {
		logger.error("Error Message : " + e.getErrMsg());		
		return e.getErrMsg();
	}
	

}
