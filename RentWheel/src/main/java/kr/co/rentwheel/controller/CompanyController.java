package kr.co.rentwheel.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.rentwheel.domain.ResponseItem;
import kr.co.rentwheel.dto.CompanyDto;
import kr.co.rentwheel.service.CompanyService;

@Controller
public class CompanyController {
	@Autowired
	private CompanyService companyService;

	// **** 해당 업체 정보 조회 ****//
	@RequestMapping(value = "/companySelect", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject companySelect(HttpServletRequest req, ModelMap model) {
		String c_id = req.getParameter("c_id");
		JSONObject obj = companyService.companySelect(c_id);

		return obj;
	}

	// **** 업체 가입 ****//
	@RequestMapping(value = "/companyJoin", method = RequestMethod.POST)
	@ResponseBody
	public ResponseItem companyJoin(HttpServletRequest req) {
		CompanyDto companyDto = new CompanyDto();
		companyDto.setC_id(req.getParameter("c_id"));
		companyDto.setC_phone(req.getParameter("c_phone"));
		companyDto.setC_address(req.getParameter("c_address"));
		companyDto.setC_email(req.getParameter("c_email"));
		companyDto.setC_x_coord(req.getParameter("c_x_coord"));
		companyDto.setC_y_coord(req.getParameter("c_y_coord"));
		companyDto.setC_name(req.getParameter("c_name"));
		companyDto.setC_represent_name(req.getParameter("c_represent_name"));
		companyDto.setC_acnt_num(req.getParameter("c_acnt_num"));
		companyDto.setC_acnt_bank(req.getParameter("c_acnt_bank"));
		companyDto.setC_open_time(req.getParameter("c_open_time"));
		companyDto.setC_close_time(req.getParameter("c_close_time"));
		companyDto.setC_m_count(Integer.parseInt(req.getParameter("c_m_count")));
		companyDto.setC_area_id(Integer.parseInt(req.getParameter("c_area_id")));

		// companyService의 서비스 단만 반환형을 ResponseItem으로 하고 DAO단은 반환형 boolean으로 함
		// 서비스단에서 ResponseItem을 만들어서 반환하고 컨트롤러는 그것을 그대로 반환
		return companyService.companyJoin(companyDto);
	}
	
	
	// **** 지역에 따른 업체리스트 조회 ****//
	@RequestMapping(value = "/companies", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray companies(HttpServletRequest req){
		String str = req.getParameter("c_area_id");
		JSONArray jsonArr = new JSONArray();
		if(str == null)
			jsonArr = companyService.companiesAll();
		else
			jsonArr = companyService.companies(Integer.parseInt(str));
		
		return jsonArr;
	}
	
}
