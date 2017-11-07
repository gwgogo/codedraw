package kr.co.rentwheel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.rentwheel.dao.HolidayDao;
import kr.co.rentwheel.dto.HolidayDto;
import kr.co.rentwheel.service.HolidayService;

@Service
public class HolidayServiceImpl implements HolidayService {
	@Autowired
	private HolidayDao holidayDao;
	public List<HolidayDto> getCompanyHoliday(String h_company_id){
		return holidayDao.getCompanyHoliday(h_company_id);
	}
}
