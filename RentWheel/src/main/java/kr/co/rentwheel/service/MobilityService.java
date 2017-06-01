package kr.co.rentwheel.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public interface MobilityService {
	public JSONArray mobilitySelect(String c_id);
	public JSONArray mobilitySelectAll();
	public JSONObject mobilityId(String m_id);
}
