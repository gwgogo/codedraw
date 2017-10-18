package kr.co.rentwheel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.rentwheel.dto.HolidayDto;
import kr.co.rentwheel.service.HolidayService;

@RestController
@RequestMapping(value="/holiday")
public class HolidayController {

	@Autowired
	private HolidayService holidayService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<HolidayDto> getCompanyHoliday(@RequestParam("h_company_id")String h_company_id){
		return holidayService.getCompanyHoliday(h_company_id);
	}
	
}
