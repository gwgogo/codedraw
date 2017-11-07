package kr.co.rentwheel.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.rentwheel.domain.CustomException;
import kr.co.rentwheel.domain.ResponseItem;
import kr.co.rentwheel.dto.UserDto;
import kr.co.rentwheel.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseItem userLogin(@RequestParam("u_id")String u_id, @RequestParam("u_pw")String u_pw) {
		return userService.userLogin(u_id, u_pw);
	}

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public ResponseItem userJoin(HttpServletRequest req) {
		UserDto user = new UserDto();
		user.setU_id(req.getParameter("u_id"));
		user.setU_pw(req.getParameter("u_pw"));
		user.setU_company_id(req.getParameter("u_company_id"));
		/*user.setU_phone(req.getParameter("u_phone"));
		user.setU_name(req.getParameter("u_name"));
		user.setU_age(Integer.parseInt(req.getParameter("u_age")));
		user.setU_address(req.getParameter("u_address"));*/
		ResponseItem responseItem = userService.userJoin(user);

		return responseItem;
	}
	
	/* 아이디 중복 조회 */
	@RequestMapping(value = "/join/validation", method = RequestMethod.POST)
	public ResponseItem userJoinDuplicationValidate(@RequestParam("u_id")String u_id) {
		ResponseItem responseItem = userService.userJoinDuplicationValidate(u_id);
		return responseItem;
	}


	@RequestMapping(value = "/{u_id}", method=RequestMethod.GET)
	public UserDto getUserByUserID(@PathVariable("u_id") String u_id){
		return userService.getUserByUserID(u_id);
	}
	
	
	@RequestMapping(method=RequestMethod.GET)
	public List<UserDto> getUserAll() {
		return userService.getUserAll();
	}
	
	/* 사용자 잔액 충전 */
	@RequestMapping(value = "/balance", method=RequestMethod.POST)
	public ResponseItem addUserBalance(@RequestParam("u_id")String u_id, @RequestParam("u_balance")int u_balance) throws CustomException {
		return userService.increaseUserBalance(u_id, u_balance);
	}
	
	/* 사용자 잔액 조회 */
	@RequestMapping(value = "/balance", method=RequestMethod.GET)
	public int getUserBalance(@RequestParam("u_id")String u_id) {
		return userService.getUserBalance(u_id);
	}
	
}

