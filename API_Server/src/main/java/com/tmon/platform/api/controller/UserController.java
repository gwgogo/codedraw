package com.tmon.platform.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmon.platform.api.domain.ResponseItem;
import com.tmon.platform.api.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/loginForm")
	public String loginForm() {
		return "loginForm";
	}
	
	
	@RequestMapping("/login")
	@ResponseBody
	public ResponseItem login(@RequestParam("user_id")String user_id, @RequestParam("user_pw") String user_pw) {
		
		return userService.login(user_id, user_pw);
	
	}
}
