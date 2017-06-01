package kr.co.rentwheel.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import kr.co.rentwheel.domain.ResponseItem;
import kr.co.rentwheel.dto.CompanyDto;

public interface CompanyService {
	public JSONObject companySelect(String c_id);
	public ResponseItem companyJoin(CompanyDto dto);
	public JSONArray companies(int c_area_id);
	public JSONArray companiesAll();
}
