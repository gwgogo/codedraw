package kr.co.rentwheel.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.rentwheel.domain.ResponseItem;
import kr.co.rentwheel.dto.AdministratorDto;
import kr.co.rentwheel.dto.CompanyDto;
import kr.co.rentwheel.dto.MobilityDto;
import kr.co.rentwheel.dto.ReservationDto;
import kr.co.rentwheel.dto.UserDto;
import kr.co.rentwheel.service.CompanyService;
import kr.co.rentwheel.service.MobilityService;
import kr.co.rentwheel.service.ReservationService;
import kr.co.rentwheel.service.UserService;

/*
 * u_auth 
 * 1 : �����
 * 2 : ��ü
 * 3 : ���
 * 
 */
@Controller
@RequestMapping(value="/admin")
public class AdminController {
	private static final int ADMIN_AUTH = 3;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private MobilityService mobilityService;
	
	@Autowired
	private ReservationService reservationService;
	
	
	@RequestMapping(method=RequestMethod.GET)
	public String loginForm() {
		return "loginForm";
	}
	@RequestMapping(value="/home", method=RequestMethod.GET)
	public String home() {
		return "home";
	}
	
	
	/* ���� ��ȿ�� üũ �� ������ ���� üũ */
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String adminLogin(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String u_id = req.getParameter("u_id");
		String u_pw = req.getParameter("u_pw");
		
		UserDto userDto = userService.getUserByUserID(u_id);
		ResponseItem responseItem = userService.userLogin(u_id, u_pw);
		
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		
		if(!responseItem.getResult().equals("200")) {
			out.println("<script>alert('���̵� �Ǵ� �н����尡 Ʋ���ϴ�.');</script>");
			out.flush();
			return "loginForm";
		}
		
		int u_auth = userDto.getU_auth();
		if(ADMIN_AUTH != u_auth) {
			out.println("<script>alert('������ ������ �����ϴ�.');</script>");
			out.flush();
			return "loginForm";
		}
		
		return "home";
	}
	
	@RequestMapping(value="/administrator", method=RequestMethod.GET)
	public String getAdminiStrator(ModelMap modelMap) {
		AdministratorDto dto = userService.getAdministrator();
		modelMap.put("administrator", dto);
		return "administrator";
	}
	
	/* ��� ��ü ����Ʈ ��ȸ */
	@RequestMapping(value="/company", method=RequestMethod.GET)
	public String getCompanyList(ModelMap modelMap) {
		List<CompanyDto> companyList = companyService.getCompanyAll();
		modelMap.put("companyList", companyList);
		return "company";
	}
	
	/* ��� �����Ƽ ����Ʈ ��ȸ */
	@RequestMapping(value="/mobility", method=RequestMethod.GET)
	public String getMobilityList(@RequestParam(value="c_id", required=false) String c_id, ModelMap modelMap) {
		List<MobilityDto> mobilityList = new ArrayList<MobilityDto>();
		String returnUrl = "";
		if(c_id == null) {
			mobilityList = mobilityService.getMobilityAll();
			returnUrl = "mobility";
		}
		else {
			mobilityList = mobilityService.getMobilityByCompanyID(c_id);
			returnUrl = "companyMobility";
		}
		modelMap.put("mobilityList", mobilityList);
		return returnUrl;
	}
	
	/* ����� ����Ʈ ��ȸ */
	@RequestMapping(value="/user", method=RequestMethod.GET)
	public String getUserList(ModelMap modelMap) {
		List<UserDto> userList = userService.getUserAll();
		for(int i = 0; i < userList.size(); i++) {
			UserDto dto = userList.get(i);
			if(dto.getU_auth() == 1) {
				dto.setU_auth_string("�����");
			}
			else if(dto.getU_auth() == 2) {
				dto.setU_auth_string("��ü");
			}
			else if(dto.getU_auth() == 3) {
				dto.setU_auth_string("���");
			}
		}
		
		modelMap.put("userList", userList);
		return "user";
	}
	
	/* ���೻�� ����Ʈ ��ȸ */
	@RequestMapping(value="/reservation", method=RequestMethod.GET)
	public String getReservationList(ModelMap modelMap) {
		List<ReservationDto> reservationList = reservationService.getReservationByAdmin();
		modelMap.put("reservationList", reservationList);
		return "reservation";
	}
	
	
	
}
