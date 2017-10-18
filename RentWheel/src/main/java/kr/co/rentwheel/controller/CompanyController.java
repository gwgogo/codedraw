package kr.co.rentwheel.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.rentwheel.domain.ResponseItem;
import kr.co.rentwheel.dto.CompanyDto;
import kr.co.rentwheel.service.CompanyService;

@RestController
@RequestMapping(value="/company")
public class CompanyController {
	@Autowired
	private CompanyService companyService;

	/* 업체번호에 따른 업체 정보 조회 */
	@RequestMapping(method = RequestMethod.GET)
	public CompanyDto getCompanyByCompanyID(@RequestParam("c_id") String c_id, ModelMap model) {
		return companyService.getCompanyByCompanyID(c_id);
	}
	
	/* 지역번호에 따른 업체리스트 조회 */
	@RequestMapping(value = "/area", method = RequestMethod.GET)
	public List<CompanyDto> getCompanyByAreaID(@RequestParam(value="c_area_id", required=false) String c_area_id){
		if(c_area_id == null)
			return companyService.getCompanyAll();
		else if(c_area_id.length() == 2)	// ex) 서울 전체
			return companyService.getCompanyByAreaID(Integer.parseInt(c_area_id));
		else								// ex) 서울시 용산구
			return companyService.getCompanyByRegionID(Integer.parseInt(c_area_id));
	}
	
	/* 업체 가입 */
	@RequestMapping(method = RequestMethod.POST)
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
	
}
