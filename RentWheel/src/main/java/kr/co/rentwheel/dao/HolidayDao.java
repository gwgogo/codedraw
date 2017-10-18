package kr.co.rentwheel.dao;

import java.util.List;

import kr.co.rentwheel.dto.HolidayDto;

public interface HolidayDao {
	public List<HolidayDto> getCompanyHoliday(String h_company_id);
}
