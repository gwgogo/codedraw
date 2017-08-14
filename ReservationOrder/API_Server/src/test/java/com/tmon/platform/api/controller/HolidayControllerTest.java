package com.tmon.platform.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.Cookie;

import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.tmon.platform.api.dto.HolidayDto;
import com.tmon.platform.api.exception.AbstractCustomException;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.exception.SQLCustomException;
import com.tmon.platform.api.exception.StopServiceException;
import com.tmon.platform.api.service.HolidayService;

import net.minidev.json.parser.JSONParser;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "file:src/test/resources/testServlet-context.xml",
		"file:src/test/resources/testDataSource-context.xml"})
public class HolidayControllerTest {
	@Autowired
	public WebApplicationContext wac;
	public MockMvc mockMvc;
	public String validSession;
	public int defaultHolidayID;
	private static final String PREFIX = "/holidays";
	private static final String USER_ID = "user0001";
	private static final String USER_PW = "user0001";
	private static final String ADMIN_ID = "admin0001";
	private static final String ADMIN_PW = "admin0001";

	private static final String DUMMY_INSERT_TITLE = "내생일";
	private static final String DUMMY_INSERT_YEAR = "2017-12-09";

	@Autowired
	HolidayService holidayService;

	@Before
	public void 공휴일준비() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

		try {
			holidayService.insert(DUMMY_INSERT_YEAR, DUMMY_INSERT_TITLE);
		} catch (AbstractCustomException e) {
			// 이미 중복데이터가 들어가있는 경우, 기존 거 쓰면 되니까 필요없다.
			String errCode = e.getErrorStatus().get("errCode");
			if (false == errCode.equals("617")) {
				// "Fail Insert HOLIDAY SQL Error" because PK constraint
				e.printStackTrace();
			}
		}

		defaultHolidayID = 연도랑_이름으로ID가져오기(2017, "내생일");
	}

	@After
	public void 뒷정리() throws Exception {
		ID있으면삭제하기(defaultHolidayID);
		로그아웃();
	}
	
	
	//////////////////////////////////////// TEST_START ////////////////////////////////////////
	@Test
	public void 정상조회() throws Exception {
		로그인(ADMIN_ID, ADMIN_PW);
		mockMvc.perform(get(PREFIX + "/{year}", 1991).header("Cookie", "session=" + validSession)
				.cookie(new Cookie("session", validSession)))
				.andDo(print())
				.andExpect(status().isOk());
	}

	/**
	 * gwlee Failures 처리 // -1 인 경우 아마 쿼리에서 signed 로 캐스트되기 때문에 1을 찾아내려는 것 같습니다. 결과는 [] 입니다.
	 * 그렇다면 (-정상값) 의 경우도 정상값을 찾아내는데, 문제가 발생하지 않을지 모르겠습니다.
	 */
	@Test
	public void 조회시잘못된마이너스값() throws Exception {
		로그인(ADMIN_ID, ADMIN_PW);
		mockMvc.perform(get(PREFIX + "/{year}", -1).header("Cookie", "session=" + validSession)
				.cookie(new Cookie("session", validSession)))
				.andDo(print())
				.andExpect(jsonPath("errCode").value(617));
	}

	//617 = Fail HOLIDAY SQL Error, but 623(Controller Level Argument Type Mismatch Error)
	@Test
	public void 조회시잘못된스트링값() throws Exception {
		로그인(ADMIN_ID, ADMIN_PW);
		mockMvc.perform(get(PREFIX + "/{year}", "abcde").header("Cookie", "session=" + validSession)
				.cookie(new Cookie("session", validSession)))
				.andExpect(jsonPath("errCode").value(623));
	}

	@Test
	public void 정상삽입() throws Exception {
		로그인(ADMIN_ID, ADMIN_PW);
		JSONObject requestObject = 오브젝트만들기("1991-12-25", "성탄절");
		mockMvc.perform(post(PREFIX)
				.content(requestObject.toJSONString()).header("Cookie", "session=" + validSession)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.cookie(new Cookie("session", validSession)))
				.andExpect(status().isOk());

		int insertedID = 연도랑_이름으로ID가져오기(1991, "성탄절");
		ID있으면삭제하기(insertedID);
	}

	@Test
	public void 중복삽입() throws Exception {
		로그인(ADMIN_ID, ADMIN_PW);
		JSONObject requestObject = 오브젝트만들기(DUMMY_INSERT_YEAR, DUMMY_INSERT_TITLE);
		
		mockMvc.perform(post(PREFIX)
				.content(requestObject.toJSONString()).header("Cookie", "session=" + validSession)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.cookie(new Cookie("session", validSession)))
				.andDo(print())
				.andExpect(jsonPath("errCode").value(617));
	}

	@Test
	public void 값_거꾸로삽입() throws Exception {
		로그인(ADMIN_ID, ADMIN_PW);
		JSONObject requestObject = 오브젝트만들기(DUMMY_INSERT_TITLE, DUMMY_INSERT_YEAR);
		mockMvc.perform(post(PREFIX)
				.content(requestObject.toJSONString()).header("Cookie", "session=" + validSession)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.cookie(new Cookie("session", validSession)))
				.andExpect(jsonPath("errCode").value(616)); // "Incorrect input Date data"
	}

	@Test
	public void 비로그인유저_조회() throws Exception {
		mockMvc.perform(get(PREFIX + "/{year}", 1991))
		.andDo(print())
		.andExpect(jsonPath("errCode").value(606));
	}

	@Test
	public void 비관리자유저_조회() throws Exception {
		로그인(USER_ID, USER_PW);
		mockMvc.perform(get(PREFIX + "/{year}", 1991)
				.header("Cookie", "session=" + validSession).cookie(new Cookie("session", validSession)))
				.andExpect(jsonPath("errCode").value(607));
	}

	@Test
	public void 정상업데이트() throws Exception {
		로그인(ADMIN_ID, ADMIN_PW);
		JSONObject requestObject = 오브젝트만들기("9999-12-09", DUMMY_INSERT_YEAR);
		mockMvc.perform(put(PREFIX + "/{holidayID}", defaultHolidayID)
				.content(requestObject.toJSONString())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.header("Cookie", "session=" + validSession).cookie(new Cookie("session", validSession)))
				.andExpect(status().isOk());
	}

	@Test
	public void 이상한날짜로업데이트() throws Exception {
		로그인(ADMIN_ID, ADMIN_PW);
		JSONObject requestObject = 오브젝트만들기("9999-99-99", DUMMY_INSERT_YEAR);
		mockMvc.perform(put(PREFIX + "/{holidayID}", defaultHolidayID)
				.content(requestObject.toJSONString())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.header("Cookie", "session=" + validSession).cookie(new Cookie("session", validSession)))
				.andExpect(jsonPath("errCode").value(617));
		// 616 "Incorrect input Date data" 가 발생할 줄 알았으나, Date 형태는 지켰기 때문에 이를 통과하고 SQL 에서 617 Exception 발생
	}

	@Test
	public void 날짜안넣고업데이트() throws Exception {
		로그인(ADMIN_ID, ADMIN_PW);
		JSONObject requestObject = 오브젝트만들기("", DUMMY_INSERT_YEAR);
		mockMvc.perform(put(PREFIX + "/{holidayID}", defaultHolidayID)
				.content(requestObject.toString())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.header("Cookie", "session=" + validSession).cookie(new Cookie("session", validSession)))
				.andExpect(jsonPath("errCode").value(616));// "Incorrect input Date data"
	}

	@Test
	public void 타이틀안넣고업데이트() throws Exception {
		로그인(ADMIN_ID, ADMIN_PW);
		JSONObject requestObject = 오브젝트만들기(DUMMY_INSERT_YEAR, "");
		mockMvc.perform(put(PREFIX + "/{holidayID}", defaultHolidayID)
				.content(requestObject.toJSONString())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.header("Cookie", "session=" + validSession).cookie(new Cookie("session", validSession)))
				.andExpect(jsonPath("errCode").value(628));// "Length of 'String' Type parameter must not be zero(0)");
	}

	@Test
	public void 일반유저가업데이트() throws Exception {
		로그인(USER_ID, USER_PW);
		JSONObject requestObject = 오브젝트만들기(DUMMY_INSERT_YEAR, "변경된내생일");
		mockMvc.perform(put(PREFIX + "/{holidayID}", defaultHolidayID)
				.content(requestObject.toJSONString())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.header("Cookie", "session=" + validSession).cookie(new Cookie("session", validSession)))
				.andExpect(jsonPath("errCode").value(607));
	}

	@Test
	public void 비로그인유저가업데이트() throws Exception {
		JSONObject requestObject = 오브젝트만들기(DUMMY_INSERT_YEAR, "변경된내생일");
		mockMvc.perform(put(PREFIX + "/{holidayID}", defaultHolidayID)
				.content(requestObject.toString())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("errCode").value(606));
	}
	
	@Test
	public void 정상삭제() throws Exception {
		로그인(ADMIN_ID, ADMIN_PW);
		mockMvc.perform(delete(PREFIX + "/{holidayID}", defaultHolidayID)
				.header("Cookie", "session=" + validSession).cookie(new Cookie("session", validSession)))
				.andExpect(status().isOk());
	}
	
	@Test
	public void 없는아이디삭제() throws Exception {
		로그인(ADMIN_ID, ADMIN_PW);
		mockMvc.perform(delete(PREFIX + "/{holidayID}", -1)
				.header("Cookie", "session=" + validSession).cookie(new Cookie("session", validSession)))
				.andExpect(jsonPath("errCode").value(622));
	}
	
	@Test
	public void 잘못된영어인자로삭제() throws Exception {
		로그인(ADMIN_ID, ADMIN_PW);
		mockMvc.perform(delete(PREFIX + "/{holidayID}", "abc")
				.header("Cookie", "session=" + validSession).cookie(new Cookie("session", validSession)))
				.andExpect(jsonPath("errCode").value(623));
		//TypeMismatch in Controller Level
	}
	
	@Test
	public void 비로그인삭제() throws Exception {
		mockMvc.perform(delete(PREFIX + "/{holidayID}", defaultHolidayID))
		.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void 일반유저삭제() throws Exception {
		로그인(USER_ID, USER_PW);
		mockMvc.perform(delete(PREFIX + "/{holidayID}", defaultHolidayID)
		.header("Cookie", "session=" + validSession).cookie(new Cookie("session", validSession)))
		.andExpect(jsonPath("errCode").value(607));
	}

	//////////////////////////////////////// TEST_END////////////////////////////////////////
	@SuppressWarnings("unchecked")
	private JSONObject 오브젝트만들기(String format, String title) throws ParseException {
		JSONObject request = new JSONObject();
		request.put("holidayDate", format);
		request.put("holidayTitle", title);

		return request;
	}

	private int 연도랑_이름으로ID가져오기(int year, String title) throws SQLCustomException {
		List<HolidayDto> results = holidayService.selectBythisYear(year);
		for (HolidayDto holiday : results) {
			if (holiday.getHolidayTitle().equals(title)) {
				return holiday.getHolidayID();
			}
		}
		return -1;
	}

	private void ID있으면삭제하기(int holidayID) throws SQLCustomException, StopServiceException, NullCustomException {
		try {
			holidayService.delete(holidayID);
		} catch (AbstractCustomException e) {
			String errCode = e.getErrorStatus().get("errCode");
			if (false == errCode.equals("622")) {
				// "There isn't HolidayDto data"
				e.printStackTrace();
			}
		}
	}

	private void 로그인(String id, String pw) throws Exception {
		MvcResult response = mockMvc.perform(post("/user/login").param("userID", id).param("userPW", pw))
				// .andDo(print())
				.andReturn();

		String responseBodstr = response.getResponse().getContentAsString();
		JSONParser parser = new JSONParser();
		JSONObject responseBody = (JSONObject) parser.parse(responseBodstr);
		validSession = responseBody.get("session").toString();
	}

	private void 로그아웃() throws Exception {
		mockMvc.perform(get("/user/logout").header("Cookie", "session=" + validSession));
		mockMvc = null;
		validSession = null;
	}
}
