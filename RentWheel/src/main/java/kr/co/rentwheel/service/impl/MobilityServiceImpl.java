package kr.co.rentwheel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.rentwheel.dao.MobilityDao;
import kr.co.rentwheel.dto.MobilityDto;
import kr.co.rentwheel.service.MobilityService;
import kr.co.rentwheel.service.ReservationService;

@Service
public class MobilityServiceImpl implements MobilityService {
	
	@Autowired
	private MobilityDao mobilityDao;
	
	@Autowired
	private ReservationService reservationService;
	
	public List<MobilityDto> getMobilityByCompanyID(String c_id){
		return mobilityDao.getMobilityByCompanyID(c_id);
	}
	
	public List<MobilityDto> getMobilityAll(){
		return mobilityDao.getMobilityAll();
	}
	
	public MobilityDto getMobilityByMobilityID(int m_id){
		return mobilityDao.getMobilityByMobilityID(m_id);
	}

	public List<MobilityDto> getNewMobility() {
		return mobilityDao.getNewMobility();
	}
	
	public List<MobilityDto> getRecommendMobility(){
		return mobilityDao.getRecommendMobility();
	}
	
	public List<MobilityDto> getPriceFilterMobility(int min, int max, String grade){
		return mobilityDao.getPriceFilterMobility(min, max, grade);
	}
	
	public String getMobilityName(int m_id) {
		return mobilityDao.getMobilityName(m_id);
	}
}
