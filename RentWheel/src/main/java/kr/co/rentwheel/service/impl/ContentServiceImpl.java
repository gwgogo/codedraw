package kr.co.rentwheel.service.impl;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.rentwheel.dao.ContentDao;
import kr.co.rentwheel.dto.ContentDto;
import kr.co.rentwheel.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService{
	@Autowired
	private ContentDao contentDao;
	
	public JSONArray contents(String ct_category){
		List<ContentDto> list = contentDao.contents(ct_category);
		JSONArray jsonArr = new JSONArray();
		ContentDto dto = new ContentDto();
		for(int i = 0; i < list.size(); i++){
			JSONObject obj = new JSONObject();
			dto = list.get(i);
			obj.put("ct_id", dto.getCt_id());
			obj.put("ct_category", dto.getCt_category());
			obj.put("ct_msg", dto.getCt_msg());
			obj.put("ct_img", dto.getCt_img());
			jsonArr.add(obj);
		}
		
		return jsonArr;
	}
}
