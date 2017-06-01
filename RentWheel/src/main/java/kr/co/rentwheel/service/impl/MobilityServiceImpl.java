package kr.co.rentwheel.service.impl;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.rentwheel.dao.MobilityDao;
import kr.co.rentwheel.dto.MobilityDto;
import kr.co.rentwheel.service.MobilityService;

@Service
public class MobilityServiceImpl implements MobilityService {
	
	@Autowired
	private MobilityDao mobilityDao;
	
	public JSONArray mobilitySelect(String c_id){
		List<MobilityDto> list = mobilityDao.mobilitySelect(c_id);
		JSONArray jsonArr = new JSONArray();
		MobilityDto dto = new MobilityDto();
		
		for(int i = 0; i < list.size(); i++){
			JSONObject obj = new JSONObject();
			dto = list.get(i);			
			obj.put("m_name", dto.getM_name());
			obj.put("m_price", dto.getM_price());
			obj.put("m_category_name", dto.getM_category_name());
			obj.put("m_img", dto.getM_img());
			obj.put("m_spec", dto.getM_spec());
			obj.put("m_rating1", dto.getM_rating1());
			obj.put("m_rating2", dto.getM_rating2());
			obj.put("m_rating3", dto.getM_rating3());
			obj.put("m_brand_name", dto.getM_brand_name());
			obj.put("m_company_name", dto.getM_company_name());
			jsonArr.add(obj);
		}
		
		return jsonArr;
	}
	
	public JSONArray mobilitySelectAll(){
		List<MobilityDto> list = mobilityDao.mobilitySelectAll();
		JSONArray jsonArr = new JSONArray();
		MobilityDto dto = new MobilityDto();
		
		for(int i = 0; i < list.size(); i++){
			JSONObject obj = new JSONObject();
			dto = list.get(i);			
			obj.put("m_name", dto.getM_name());
			obj.put("m_price", dto.getM_price());
			obj.put("m_category_name", dto.getM_category_name());
			obj.put("m_img", dto.getM_img());
			obj.put("m_spec", dto.getM_spec());
			obj.put("m_rating1", dto.getM_rating1());
			obj.put("m_rating2", dto.getM_rating2());
			obj.put("m_rating3", dto.getM_rating3());
			obj.put("m_brand_name", dto.getM_brand_name());
			obj.put("m_company_name", dto.getM_company_name());
			jsonArr.add(obj);
		}
		return jsonArr;
	}
	
	
	public JSONObject mobilityId(String m_id){
		MobilityDto dto = mobilityDao.mobilityId(m_id);
		JSONObject obj = new JSONObject();
		obj.put("m_id", dto.getM_id());
		obj.put("m_name", dto.getM_name());
		obj.put("m_price", dto.getM_price());
		obj.put("m_category_name", dto.getM_category_name());
		obj.put("m_img", dto.getM_img());
		obj.put("m_spec", dto.getM_spec());
		obj.put("m_rating1", dto.getM_rating1());
		obj.put("m_rating2", dto.getM_rating2());
		obj.put("m_rating3", dto.getM_rating3());
		obj.put("m_brand_name", dto.getM_brand_name());
		obj.put("m_company_name", dto.getM_company_name());
		
		return obj;
	}
}
