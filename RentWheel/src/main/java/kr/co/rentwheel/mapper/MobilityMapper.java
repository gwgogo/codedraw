package kr.co.rentwheel.mapper;

import java.util.List;
import java.util.Map;

import kr.co.rentwheel.dto.MobilityDto;

public interface MobilityMapper {
	public List<MobilityDto> getMobilityByCompanyID(String c_id);
	public List<MobilityDto> getMobilityAll();
	public MobilityDto getMobilityByMobilityID(int m_id);
	public List<MobilityDto> getNewMobility(); 
	public List<MobilityDto> getRecommendMobility();
	public List<MobilityDto> getPriceFilterMobility(Map<String,String> map);
}
