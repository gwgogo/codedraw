package kr.co.rentwheel.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.rentwheel.service.MobilityService;

@Controller
public class MobilityController {

	@Autowired
	private MobilityService mobilityService;

	// **** 모빌리티 리스트 ****//
	// **** c_id파라미터가 있으면 업체가 보유한 모빌리트 리스트, 없으면 전체 모빌리티 리스트 ****//
	@RequestMapping(value = "/mobility/select", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray mobilitySelect(HttpServletRequest req) {
		JSONArray jsonArr = new JSONArray();
		String c_id = req.getParameter("c_id");
		if(c_id == null)
			jsonArr = mobilityService.mobilitySelectAll();
		else
			jsonArr = mobilityService.mobilitySelect(c_id);
		
		return jsonArr;
	}
	
	
	@RequestMapping(value = "/mobilityId", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject mobilityId(HttpServletRequest req) {
		
		JSONObject obj = mobilityService.mobilityId(req.getParameter("m_id"));
		return obj;
	}
}
