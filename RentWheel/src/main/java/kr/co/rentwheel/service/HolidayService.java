package kr.co.rentwheel.service;

import java.util.List;

import kr.co.rentwheel.dto.HolidayDto;

public interface HolidayService {
	public List<HolidayDto> getCompanyHoliday(String h_company_id);
}
