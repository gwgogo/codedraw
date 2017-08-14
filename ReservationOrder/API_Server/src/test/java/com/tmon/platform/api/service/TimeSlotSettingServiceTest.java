package com.tmon.platform.api.service;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.tmon.platform.api.dto.TimeSlotSettingDto;
import com.tmon.platform.api.exception.DateFormatException;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.exception.SQLCustomException;
import com.tmon.platform.api.service.TimeSlotSettingService;

//TODO 문제, startTime, endTime 같은 값 여러개 들어갈 수 있음
//TODO 문제, delete에서 마이너스 값 확인 하지 않음
//TODO 문제, Insert에서는 HH:mm 이 동작하나 Update에서는 HH:mm:ss 만 동작함
/**
 * TimeSlotSettingService Test. 타임슬롯템플릿의 CRUD를 담당하는 Service.
 * 
 * Controller | Interceptor -> Service 인데, Service 자체에서는 권한문제가 없습니다. 그래서 CRUD가
 * 정상 작동하는지의 테스트만 진행합니다.
 * 
 * @author gwlee
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "file:src/test/resources/testServlet-context.xml",
		"file:src/test/resources/testDataSource-context.xml"})
public class TimeSlotSettingServiceTest {
	@Autowired
	public TimeSlotSettingService settingService;

	@Before
	public void 삽입하기() throws DateFormatException, SQLCustomException {
		settingService.insert("22:50:00", "22:59:00", "13:00:00");
	}

	@After
	public void 삽입된값_삭제하기() throws SQLCustomException, DateFormatException, NullCustomException {
		int insertedID = -1;
		List<TimeSlotSettingDto> result;
		result = settingService.select();
		for (TimeSlotSettingDto item : result) {
			if (item.getStartTime().toString().equals("22:50:00")) {
				insertedID = item.getTimeslotSettingID();
				break;
			}
		}

		if (insertedID == -1)
			Assert.fail(); // 삽입이 안되었다는뜻?
		

		settingService.delete(insertedID); // 왜 DateFormatException을 발생시키는지?
	}
	
	@Test
	public void 조회하기() {
		try {
			List<TimeSlotSettingDto> result = settingService.select();

			System.out.println("-----------------------result-----------------------");
			for (TimeSlotSettingDto item : result) {
				System.out.println(item.getTimeslotSettingID() + " : " + item.getStartTime() + " ~ " + item.getEndTime()
						+ " / " + item.getCutoff());
			}
			System.out.println("----------------------------------------------------");
			// 결과 반환 가능시 정상단정
		} catch (SQLCustomException e) {
			Assert.fail();
		}
	}

	@Test(expected = SQLCustomException.class)
	public void 이미넣은거또넣기() throws DateFormatException, SQLCustomException {
		settingService.insert("22:50:00", "22:59:00", "13:00:00");
	}

	@Test(expected = SQLCustomException.class)
	public void 시작시간만같은거넣기() throws DateFormatException, SQLCustomException {
		settingService.insert("22:50:00", "22:58:00", "12:00:00");
	}

	@Test(expected = DateFormatException.class)
	public void 끝시간이_시작시간보다이전인값_넣기() throws DateFormatException, SQLCustomException {
		settingService.insert("12:31:00", "12:29:00", "12:00:00");
	}

	@Test(expected = DateFormatException.class)
	public void 시작시간과_끝시간이_같은값_넣기() throws DateFormatException, SQLCustomException {
		settingService.insert("12:34:00", "12:34:00", "12:00:00");
	}

	@Test(expected = DateFormatException.class)
	public void 세값이_전부_같은값_넣기() throws DateFormatException, SQLCustomException {
		settingService.insert("11:11:00", "11:11:00", "11:11:00");
	}

	// 시작시간을 통해 조회하는 방법이 없네요
	@Test
	public void 삽입된값_정상변경하기() throws NullCustomException {
		int insertedID = -1;
		List<TimeSlotSettingDto> result;
		try {
			result = settingService.select();
			for (TimeSlotSettingDto item : result) {
				if (item.getStartTime().toString().equals("22:50:00")) {
					insertedID = item.getTimeslotSettingID();
					break;
				}
			}

			if (insertedID == -1)
				Assert.fail(); // 삽입이 안되었다는뜻?
			// 22:50 -> 12:50
			
			
			settingService.update(insertedID, "12:50:00", "12:59:00", "13:00:00");
			settingService.update(insertedID, "22:50:00", "22:59:00", "13:00:00");
		} catch (SQLCustomException | DateFormatException e) {
			Assert.fail();
		}
	}

	@Test(expected = DateFormatException.class)
	public void 삽입된값_끝시간시작시간_바꿔서넣기() throws SQLCustomException, DateFormatException, NullCustomException {
		int insertedID = -1;
		List<TimeSlotSettingDto> result;
		result = settingService.select();
		for (TimeSlotSettingDto item : result) {
			if (item.getStartTime().toString().equals("22:50:00")) {
				insertedID = item.getTimeslotSettingID();
				break;
			}
		}

		if (insertedID == -1)
			Assert.fail(); // 삽입이 안되었다는뜻?
		
		
		settingService.update(insertedID, "22:59:00", "22:50:00", "13:00:00"); // 여기서 에러가 발생해야한다
		settingService.update(insertedID, "22:50:00", "22:59:00", "13:00:00"); // 에러가 안나면 정상행(assert false)
	}

	@Test(expected = Exception.class)
	public void 마이너스일_ID로넣어서_삭제하기() throws DateFormatException, SQLCustomException, NullCustomException {
		settingService.delete(-1);
	}
}
