package kr.co.rentwheel.dao;

import java.util.List;

import org.json.simple.JSONArray;

import kr.co.rentwheel.dto.MobilityDto;

public interface MobilityDao {
	public List<MobilityDto> mobilitySelect(String c_id);
	public List<MobilityDto> mobilitySelectAll();
	public MobilityDto mobilityId(String m_id);
}
