package com.tmon.platform.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
<<<<<<< HEAD
=======
import org.springframework.web.bind.annotation.CrossOrigin;
>>>>>>> cb9bf97e921d0ba126db26909b9d8211bab8c3db
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmon.platform.api.dto.UserDto;
import com.tmon.platform.api.service.UserService;
import com.tmon.platform.api.util.CustomException;

<<<<<<< HEAD
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(value="사용자(or 관리자)에 대한 API")
=======
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;


@CrossOrigin
>>>>>>> cb9bf97e921d0ba126db26909b9d8211bab8c3db
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
		logger.info("/mypage");
		return "mypage";
	}
	
<<<<<<< HEAD
=======
	@RequestMapping(value="/admin", method = RequestMethod.GET)
	public String admin() {
		return "admin";
	}
>>>>>>> cb9bf97e921d0ba126db26909b9d8211bab8c3db
	
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
	 * @param user_id 유저아이디
	 * @param user_pw 유저비밀번호
	 * @return success : {session : 세션키} / fail : CustomException
	 * @throws CustomException {msg : Invalid User Information}
	 */
	@ApiOperation(value="로그인", notes="로그인 성공시 API서버에서 세션키 발급 후 UI서버에서 쿠키에 저장")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject login(@RequestParam("user_id") String user_id, @RequestParam("user_pw") String user_pw) throws CustomException {
		return userService.login(user_id, user_pw);
	}
	
<<<<<<< HEAD
=======
	
>>>>>>> cb9bf97e921d0ba126db26909b9d8211bab8c3db
	@ApiOperation(value="로그아웃", notes="세션삭제를 위한 로그아웃")
	@ApiImplicitParam(name = "session", value = "현재 사용자의 세션 값", dataType = "string", paramType = "query")
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject logout(@RequestParam("session") String session) throws CustomException {
		return userService.logout(session);
	}

	
	@ApiOperation(value="회원가입", notes="현재는 ID, PW 만으로 회원가입 가능")
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject join(@RequestParam("user_id") String user_id, @RequestParam("user_pw") String user_pw) throws Exception {
		UserDto userDto = new UserDto();
		userDto.setUser_id(user_id);
		userDto.setUser_pw(user_pw);
		return userService.join(userDto);
	}
	
	
	@ApiOperation(value="사용자 정보 조회", notes="MyPage등에 사용할 사용자 정보 조회 - 핸들러 인터셉터에서 세션 검사")
	@ApiImplicitParam(name = "session", value = "현재 사용자의 세션 값", dataType = "string", paramType = "query")
	@RequestMapping(value="/mypageData", method = RequestMethod.GET)
	@ResponseBody
<<<<<<< HEAD
	public UserDto mypageData(@RequestParam("session")String session) {
		logger.info("/mypageData");
		return userService.user(session);
	}
	
	
	@RequestMapping(value="/admin", method = RequestMethod.GET)
	public String admin() {
		return "admin";
	}
=======
	public UserDto mypageData(HttpServletRequest request) {
		String header = request.getHeader("session");
		logger.info("컨트롤러 헤더 : " + header);
		String session = request.getParameter("session");
		logger.info("인터셉터에서 /mypageData 컨트롤러로 넘어온 session : " + session);
		UserDto userDto = userService.user(session);
		logger.info("user_id : " + userDto.getUser_id());
		return userDto;
	}
	
	
	
	
>>>>>>> cb9bf97e921d0ba126db26909b9d8211bab8c3db

}
