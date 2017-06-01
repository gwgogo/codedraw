package kr.co.rentwheel.service.impl;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.rentwheel.dao.CompanyDao;
import kr.co.rentwheel.domain.ResponseItem;
import kr.co.rentwheel.dto.CompanyDto;
import kr.co.rentwheel.service.CompanyService;

@Service
public class CompanyServiceImpe implements CompanyService {

	@Autowired
	private CompanyDao companyDao;

	public JSONObject companySelect(String c_id) {
		CompanyDto companyDto = companyDao.companySelect(c_id);
		JSONObject obj = new JSONObject();
		if (companyDto == null) {
			obj.put("result", "408");
			obj.put("msg", "Fail : not exist company");
		} else {
			obj.put("c_id", companyDto.getC_id());
			obj.put("c_phone", companyDto.getC_phone());
			obj.put("c_address", companyDto.getC_address());
			obj.put("c_email", companyDto.getC_email());
			obj.put("c_x_coord", companyDto.getC_x_coord());
			obj.put("c_y_coord", companyDto.getC_y_coord());
			obj.put("c_name", companyDto.getC_name());
			obj.put("c_represent_name", companyDto.getC_represent_name());
			obj.put("c_acnt_num", companyDto.getC_acnt_num());
			obj.put("c_acnt_bank", companyDto.getC_acnt_bank());
			obj.put("c_open_time", companyDto.getC_open_time());
			obj.put("c_close_time", companyDto.getC_close_time());
			obj.put("c_m_count", companyDto.getC_m_count());
			obj.put("c_area_id", companyDto.getC_area_id());
		}

		return obj;
	}

	public ResponseItem companyJoin(CompanyDto dto) {
		ResponseItem responseItem = new ResponseItem();
		if (!companyDao.companyJoin(dto)) {
			responseItem.setMsg("Fail : companyJoin Error");
			responseItem.setResult("407");
		}

		return responseItem;
	}

	public JSONArray companiesAll() {
		List<CompanyDto> list = companyDao.companiesAll();
		JSONArray jsonArr = new JSONArray();
		CompanyDto companyDto = new CompanyDto();
		for (int i = 0; i < list.size(); i++) {
			JSONObject obj = new JSONObject();
			companyDto = list.get(i);
			obj.put("c_id", companyDto.getC_id());
			obj.put("c_name", companyDto.getC_name());
			obj.put("c_address", companyDto.getC_address());
			obj.put("c_x_coord", companyDto.getC_x_coord());
			obj.put("c_y_coord", companyDto.getC_y_coord());
			obj.put("c_open_time", companyDto.getC_open_time());
			obj.put("c_close_time", companyDto.getC_close_time());
			obj.put("c_m_count", companyDto.getC_m_count());
			jsonArr.add(obj);
		}

		return jsonArr;
	}

	public JSONArray companies(int c_area_id) {
		List<CompanyDto> list = companyDao.companies(c_area_id);

		JSONArray jsonArr = new JSONArray();
		CompanyDto companyDto = new CompanyDto();
		for (int i = 0; i < list.size(); i++) {
			JSONObject obj = new JSONObject();
			companyDto = list.get(i);
			obj.put("c_id", companyDto.getC_id());
			obj.put("c_name", companyDto.getC_name());
			obj.put("c_address", companyDto.getC_address());
			obj.put("c_x_coord", companyDto.getC_x_coord());
			obj.put("c_y_coord", companyDto.getC_y_coord());
			obj.put("c_open_time", companyDto.getC_open_time());
			obj.put("c_close_time", companyDto.getC_close_time());
			obj.put("c_m_count", companyDto.getC_m_count());
			jsonArr.add(obj);
		}

		return jsonArr;
	}
}
