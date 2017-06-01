package kr.co.rentwheel.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import kr.co.rentwheel.domain.ResponseItem;
import kr.co.rentwheel.dto.CompanyDto;
import kr.co.rentwheel.dto.UserDto;
import kr.co.rentwheel.service.CompanyService;
import kr.co.rentwheel.service.ContentService;
import kr.co.rentwheel.service.LoginService;
import kr.co.rentwheel.service.MobilityService;

@Controller
@SessionAttributes({ "u_id", "u_pw" })
public class UserController {

	@Autowired
	private LoginService loginService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private MobilityService mobilityService;

	@Autowired
	private ContentService contentService;

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate);
		return "home";
	}

	// **** 로그인 ****//
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ResponseItem login(HttpServletRequest request, HttpSession session) {
		String u_id = request.getParameter("u_id");
		String u_pw = request.getParameter("u_pw");
		ResponseItem responseItem = loginService.login(u_id, u_pw);

		// UserDto user;
		// session.setAttribute("u_id", user.getU_id());
		return responseItem;
	}

	// **** 아이디 중복 조회 ****//
	@RequestMapping(value = "/join/isExistId", method = RequestMethod.POST)
	@ResponseBody
	public ResponseItem checkId(HttpServletRequest req) {
		String u_id = req.getParameter("u_id");
		ResponseItem responseItem = loginService.isExistId(u_id);

		return responseItem;
	}

	// **** 사용자 회원가입 ****//
	@RequestMapping(value = "/userJoin", method = RequestMethod.POST)
	@ResponseBody
	public ResponseItem userJoin(HttpServletRequest req) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		UserDto user = new UserDto();
		user.setU_id(req.getParameter("u_id"));
		user.setU_pw(req.getParameter("u_pw"));
		/*user.setU_phone(req.getParameter("u_phone"));
		user.setU_name(req.getParameter("u_name"));
		user.setU_age(Integer.parseInt(req.getParameter("u_age")));
		user.setU_address(req.getParameter("u_address"));*/
		user.setU_registDatetime(sdf.format(date));
		user.setU_company_id(req.getParameter("u_company_id"));

		ResponseItem responseItem = loginService.userJoin(user);

		return responseItem;
	}

	// **** 카테고리번호에 따른 이벤트배너, 추천배너 리스트 ****//
	@RequestMapping(value = "/contents", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray contents(HttpServletRequest req) {
		String ct_category = req.getParameter("ct_category");
		JSONArray jsonArr = contentService.contents(ct_category);

		return jsonArr;
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test(HttpServletRequest req) {
		return "test";
	}

	/* 세션 테스트 코딩 ㅠㅠㅠ */
	/*
	 * @RequestMapping(value = "/logout", method = {RequestMethod.GET,
	 * RequestMethod.POST}) public ResponseItem logout(HttpServletRequest req){
	 * 
	 * ResponseItem responseItem = new ResponseItem(); HttpSession session =
	 * req.getSession(); if(session != null) session.invalidate();
	 * 
	 * return responseItem; }
	 * 
	 * @RequestMapping(value="/test") public String test(HttpServletRequest
	 * request, ModelMap model){
	 * 
	 * return "test"; }
	 * 
	 * @RequestMapping(value="/view") public String view(HttpSession session,
	 * HttpServletRequest request, ModelMap model){ String u_id =
	 * request.getParameter("u_id"); String u_pw = request.getParameter("u_pw");
	 * ResponseItem responseItem = new ResponseItem();
	 * 
	 * UserDto user; if((user = loginService.login(u_id)) == null){
	 * responseItem.setMsg("Fail : not exist ID");
	 * responseItem.setResult("500"); } else if(!user.getU_pw().equals(u_pw)){
	 * responseItem.setMsg("Fail : not correct"); responseItem.setResult("500");
	 * } session.setAttribute("u_id", user.getU_id());
	 * //model.addAttribute("u_id", user.getU_id());
	 * model.addAttribute("u_name", user.getU_name());
	 * session.setAttribute("u_pw", user.getU_pw());
	 * model.addAttribute("u_phone",user.getU_phone()); return "view"; }
	 */
}
