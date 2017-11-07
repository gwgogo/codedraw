package kr.co.rentwheel.service;

import java.util.List;

import kr.co.rentwheel.dto.MobilityDto;

public interface MobilityService {
	public List<MobilityDto> getMobilityByCompanyID(String c_id);
	public List<MobilityDto> getMobilityAll();
	public MobilityDto getMobilityByMobilityID(int m_id);
	public List<MobilityDto> getNewMobility();
	public List<MobilityDto> getRecommendMobility();
	public List<MobilityDto> getPriceFilterMobility(int min, int max, String grade);
	public String getMobilityName(int m_id);
}
