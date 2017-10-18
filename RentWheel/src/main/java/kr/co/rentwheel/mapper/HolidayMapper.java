package kr.co.rentwheel.mapper;

import java.util.List;

import kr.co.rentwheel.dto.HolidayDto;

public interface HolidayMapper {
	public List<HolidayDto> getCompanyHoliday(String h_company_id);
}
