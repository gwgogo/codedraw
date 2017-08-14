package com.tmon.platform.api.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.tmon.platform.api.dto.HolidayDto;
import com.tmon.platform.api.exception.DateFormatException;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.exception.SQLCustomException;
import com.tmon.platform.api.exception.StopServiceException;


/**
 * @author 신광원
 * @description 공휴일에 관한 Test Code
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
"file:src/test/resources/testServlet-context.xml",
"file:src/test/resources/testDataSource-context.xml"})
public class HolidayServiceTest {

	int holidayID;
	String holidayDate;
	String holidayTitle;
	@Autowired
	private HolidayService holidayService;
	
	
	@Before
	public void before() throws SQLCustomException, DateFormatException, StopServiceException {
		holidayDate = "2050-08-15";
		holidayTitle = "테스트befor공휴일";
		holidayService.insert(holidayDate, holidayTitle);
	}

	
	@After
	public void after() throws SQLCustomException, StopServiceException, NullCustomException {
		int year = Integer.parseInt(holidayDate.substring(0, 4));
		
		List<HolidayDto> list = holidayService.selectBythisYear(year);
		for(int i = 0; i < list.size(); i++) {
			HolidayDto dto = list.get(i);
			if(dto.getHolidayTitle().equals("테스트befor공휴일")) {
				holidayService.delete(dto.getHolidayID());
			}
		}
	}
	
	
	@Test()
	public void 정상적인공휴일삽입() throws SQLCustomException, DateFormatException, StopServiceException, NullCustomException {
		String holidayDate = "2050-10-03";
		String holidayTitle = "테스트삽입공휴일";
		holidayService.insert(holidayDate, holidayTitle);				// 정상적인 공휴일 삽입
		
		
		int year = Integer.parseInt(holidayDate.substring(0, 4));
		List<HolidayDto> list = holidayService.selectBythisYear(year);
		for(int i = 0; i < list.size(); i++) {
			HolidayDto dto = list.get(i);
			if(dto.getHolidayTitle().equals("테스트삽입공휴일")) {
				assertThat(dto.getHolidayTitle(), is("테스트삽입공휴일"));	// 공휴일이 제대로 삽입 됐는지 테스트
				holidayService.delete(dto.getHolidayID());				// 삽입한 공휴일 삭제
			}
		}
	}
	
	
	@Test(expected=StopServiceException.class)
	public void 빈문자열삽입() throws SQLCustomException, DateFormatException, StopServiceException, NullCustomException {
		String holidayDate = "2050-10-03";
		String holidayTitle = "";
		holidayService.insert(holidayDate, holidayTitle);				// 정상적인 공휴일 삽입
		
	}
	
	
	
	@Test(expected=SQLCustomException.class)
	public void 중복공휴일삽입() throws SQLCustomException, DateFormatException, StopServiceException {
		String holidayDate = "2050-08-15";
		String holidayTitle = "테스트befor공휴일";
		holidayService.insert(holidayDate, holidayTitle);	
	}
	
	
	@Test(expected=DateFormatException.class)
	public void 잘못된Date포맷삽입() throws SQLCustomException, DateFormatException, StopServiceException {
		String holidayDate = "2050.08.15";
		String holidayTitle = "테스트삽입공휴일";
		holidayService.insert(holidayDate, holidayTitle);
	}
	
	
	@Test
	public void 공휴일조회() throws SQLCustomException {
		int year = Integer.parseInt(holidayDate.substring(0, 4));
		List<HolidayDto> list = holidayService.selectBythisYear(year);
		for(int i = 0; i < list.size(); i++) {
			assertThat(list.get(i).getHolidayTitle(), is(holidayTitle));
		}
	}
	
	
	@Test
	public void 공휴일업데이트() throws SQLCustomException, DateFormatException, StopServiceException, NullCustomException {
		int holiday_id = -1;
		int year = Integer.parseInt(holidayDate.substring(0, 4));
		List<HolidayDto> preList = holidayService.selectBythisYear(year);
		for(int i = 0; i < preList.size(); i++) {
			HolidayDto dto = preList.get(i);
			if(dto.getHolidayTitle().equals("테스트befor공휴일")) {				// before에서 삽입한 공휴일의 ID 찾기
				holiday_id = dto.getHolidayID();
			}
		}
		
		String updateHolidayTitle = "테스트update공휴일";
		String updateHolidayDate = "2055-08-15";
		holidayService.update(updateHolidayDate, updateHolidayTitle , holiday_id);	// 공휴일 업데이트
		List<HolidayDto> postList = holidayService.selectBythisYear(2055);
		for(int i = 0; i < postList.size(); i++) {
			HolidayDto dto = postList.get(i);
			if(dto.getHolidayID() == holiday_id) {
				assertTrue(dto.getHolidayTitle().equals(updateHolidayTitle));
				holidayService.delete(dto.getHolidayID());
			}
		}
	}
	
	
	
	@Test(expected = NullCustomException.class)
	public void 없는holiday업데이트() throws SQLCustomException, DateFormatException, StopServiceException, NullCustomException {
		int holiday_id = -1;
		int year = Integer.parseInt(holidayDate.substring(0, 4));
		List<HolidayDto> preList = holidayService.selectBythisYear(year);
		for(int i = 0; i < preList.size(); i++) {
			HolidayDto dto = preList.get(i);
			if(dto.getHolidayTitle().equals("테스트befor공휴일")) {				// before에서 삽입한 공휴일의 ID 찾기
				holiday_id = dto.getHolidayID();
			}
		}
		
		String updateHolidayTitle = "테스트update공휴일";
		String updateHolidayDate = "2055-08-15";
		holidayService.update(updateHolidayDate, updateHolidayTitle , 123);	// 공휴일 업데이트
	}
	
	
}
