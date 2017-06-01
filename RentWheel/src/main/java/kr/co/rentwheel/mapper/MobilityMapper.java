package kr.co.rentwheel.mapper;

import java.util.List;

import kr.co.rentwheel.dto.MobilityDto;

public interface MobilityMapper {
	public List<MobilityDto> mobilitySelect(String c_id);
	public List<MobilityDto> mobilitySelectAll();
	public MobilityDto mobilityId(String m_id);
}
