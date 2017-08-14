package com.tmon.platform.api.service;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.tmon.platform.api.dto.TimeSlotInformationDto;
import com.tmon.platform.api.exception.DateFormatException;
import com.tmon.platform.api.exception.SQLCustomException;

/**
 * @author 신광원
 * @description 타임슬롯 조회 관련 Test Code
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "file:src/test/resources/testServlet-context.xml",
		"file:src/test/resources/testDataSource-context.xml" })
public class TimeSlotServiceTest {

	
	@Autowired
	private TimeSlotService timeslotService;

	@Test
	public void 유효한타임슬롯조회() throws DateFormatException, SQLCustomException {
		String searchInitDate = "2017-07-20";
		List<TimeSlotInformationDto> userTimeslotList = timeslotService.selectValid(searchInitDate);
		assertThat(userTimeslotList.get(0).getDeliveryDate(), notNullValue());
		
		String searchFinishDate = "2017-07-24";
		List<TimeSlotInformationDto> adminTimeslotList = timeslotService.selectBydeliveryDate(searchInitDate, searchFinishDate);
		assertThat(adminTimeslotList.get(0).getDeliveryDate(), notNullValue());
	}
	
	@Test(expected=DateFormatException.class)
	public void 유효하지않은관리자타임슬롯조회() throws DateFormatException, SQLCustomException {
		String searchInitDate = "2017-08-20";
		String searchFinishDate = "2017-08-19";
		
		List<TimeSlotInformationDto> adminTimeslotList = timeslotService.selectBydeliveryDate(searchInitDate, searchFinishDate);
	}
	
	@Test(expected=DateFormatException.class)
	public void 잘못된날짜포맷으로타임슬롯조회() throws DateFormatException, SQLCustomException {
		String searchInitDate = "2017.08.20";
		
		List<TimeSlotInformationDto> adminTimeslotList = timeslotService.selectValid(searchInitDate);
	}
	
}
