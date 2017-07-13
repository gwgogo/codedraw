package com.tmon.platform.api.controller;

import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tmon.platform.api.dto.UserDto;
import com.tmon.platform.api.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
"file:src/test/resources/testServlet-context.xml",
"file:src/test/resources/testDataSource-context.xml" })
public class UserControllerTest {
	
	@Autowired
	private UserService userService;
	
	@Before
	public void before() {
		
	}
	
	
	@Test(expected=NullPointerException.class)
	public void login() throws Exception{
		userService.login("ddd", "aaa");
	}
	
	@Test(expected=SQLException.class)
	public void join() throws Exception{
		UserDto dto = new UserDto();
		dto.setUser_id("user0001");
		dto.setUser_pw("user0001");
		userService.join(dto);
	}
	
	
	
}
