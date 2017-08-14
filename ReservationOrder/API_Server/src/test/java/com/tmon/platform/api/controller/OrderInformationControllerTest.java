package com.tmon.platform.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.Cookie;

import org.json.simple.JSONArray;
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

import net.minidev.json.parser.JSONParser;

/**
 * 2017-07-20 ~ 2017-07-31 까지의 데이터가 테스트데이터로 들어가 있습니다. 취소의 경우 컬럼 삭제가 아닌 status 의
 * 변경으로만 기록되기 때문에 테스트시 데이터가 누적되기 때문에 비정상 행동만 기록했습니다. 삽입의 경우 데이터가 누적되고 삭제를 통해
 * 최초상태로 변경할 수 없기 때문에 비정상 행동만 기록했습니다. 주 테스트는 유저 세션, 컨트롤러 레벨에서의 파라미터입니다.
 * 
 * 취소 테스트에 사용되는 주문 117 은 admin0001 이 주문하였으며, timeslot_id 는 244입니다.
 * 
 * @author gwlee
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "file:src/test/resources/testServlet-context.xml",
		"file:src/test/resources/testDataSource-context.xml"})
public class OrderInformationControllerTest {
	@Autowired
	public WebApplicationContext wac;
	public MockMvc mockMvc;
	public String validSession;
	public int defaultHolidayID;
	private static final String PREFIX = "/orders";
	private static final String USER_ID = "user0001";
	private static final String USER_PW = "user0001";
	private static final String ADMIN_ID = "admin0001";
	private static final String ADMIN_PW = "admin0001";

	private static final int TEST_TIMESLOT_ID = 999; // 성공의 경우 제한있음
	private static final int TEST_PRODUCT_ID = 2;
	private static final int TEST_PRODUCT_QUANTITY = 2;
	private static final int TEST_ORDERED_ID = 117;

	@Before
	public void 목업준비() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@After
	public void 로그아웃() throws Exception {
		mockMvc.perform(get("/user/logout").header("Cookie", "session=" + validSession));
		mockMvc = null;
		validSession = null;
	}

	//////////////////////////////////////// TEST_START
	//////////////////////////////////////// ////////////////////////////////////////

	@Test
	public void 정상개인_날짜별조회() throws Exception {
		로그인(USER_ID, USER_PW);

		mockMvc.perform(get(PREFIX).param("searchInitDate", "2017-07-01").param("searchFinishDate", "2017-07-31")
				.header("Cookie", "session=" + validSession).cookie(new Cookie("session", validSession)))
				.andDo(print());
	}

	@Test
	public void 정상어드민_전체조회() throws Exception {
		로그인(ADMIN_ID, ADMIN_PW);

		mockMvc.perform(
				get(PREFIX + "/admin/all").param("searchInitDate", "2017-07-01").param("searchFinishDate", "2017-07-31")
						.header("Cookie", "session=" + validSession).cookie(new Cookie("session", validSession)))
				.andDo(print());
	}

	@Test
	public void 정상어드민_특정유저조회() throws Exception {
		로그인(ADMIN_ID, ADMIN_PW);

		mockMvc.perform(get(PREFIX + "/admin/{userID}", USER_ID).param("searchInitDate", "2017-07-01")
				.param("searchFinishDate", "2017-07-31").header("Cookie", "session=" + validSession)
				.cookie(new Cookie("session", validSession))).andDo(print());
	}

	@Test
	public void 유저가_전체조회() throws Exception {
		로그인(USER_ID, USER_PW);

		mockMvc.perform(
				get(PREFIX + "/admin/all").param("searchInitDate", "2017-07-01").param("searchFinishDate", "2017-07-31")
						.header("Cookie", "session=" + validSession).cookie(new Cookie("session", validSession)))
				.andExpect(jsonPath("errCode").value(607));
	}

	@Test
	public void 유저가_특정유저조회() throws Exception {
		로그인(USER_ID, USER_PW);

		mockMvc.perform(get(PREFIX + "/admin/{userID}", ADMIN_ID).param("searchInitDate", "2017-07-01")
				.param("searchFinishDate", "2017-07-31").header("Cookie", "session=" + validSession)
				.cookie(new Cookie("session", validSession))).andExpect(jsonPath("errCode").value(607));
	}

	@Test
	public void 어드민_앞뒤바꿔서_전체조회() throws Exception {
		로그인(ADMIN_ID, ADMIN_PW);

		mockMvc.perform(
				get(PREFIX + "/admin/all").param("searchInitDate", "2017-07-31").param("searchFinishDate", "2017-07-01")
						.header("Cookie", "session=" + validSession).cookie(new Cookie("session", validSession)))
				.andDo(print()).andExpect(jsonPath("errCode").value(616));
	}

	@Test
	public void 어드민_동일값_전체조회_바운더리체크() throws Exception {
		로그인(ADMIN_ID, ADMIN_PW);

		mockMvc.perform(
				get(PREFIX + "/admin/all").param("searchInitDate", "2017-07-01").param("searchFinishDate", "2017-07-01")
						.header("Cookie", "session=" + validSession).cookie(new Cookie("session", validSession)))
				.andExpect(status().isOk());
	}

	@Test
	public void 어드민_영문값_전체조회() throws Exception {
		로그인(ADMIN_ID, ADMIN_PW);

		mockMvc.perform(get(PREFIX + "/admin/all").param("searchInitDate", "abc").param("searchFinishDate", "def")
				.header("Cookie", "session=" + validSession).cookie(new Cookie("session", validSession)))
				.andExpect(jsonPath("errCode").value(616));
	}

	@Test
	public void 어드민_없는유저_조회() throws Exception {
		로그인(ADMIN_ID, ADMIN_PW);

		mockMvc.perform(get(PREFIX + "/admin/{userID}", "userNoOne").param("searchInitDate", "2017-07-01")
				.param("searchFinishDate", "2017-07-31").header("Cookie", "session=" + validSession)
				.cookie(new Cookie("session", validSession))).andExpect(jsonPath("errCode").value(626));
	}

	@Test
	public void 비로그인_전체조회() throws Exception {
		mockMvc.perform(get(PREFIX + "/admin/all").param("searchInitDate", "2017-07-01").param("searchFinishDate",
				"2017-07-31")).andExpect(jsonPath("errCode").value(606));
	}

	@Test
	public void 비로그인_특정유저조회() throws Exception {
		mockMvc.perform(get(PREFIX + "/admin/{userID}", ADMIN_ID).param("searchInitDate", "2017-07-01")
				.param("searchFinishDate", "2017-07-31")).andExpect(jsonPath("errCode").value(606));
	}

	// TODO 결과가 정확히 이루어지지 않고 아마 incSellQuantity 부분의 Integer.parseInt 부분에 null이 들어가서
	// 그런듯 하다.
	@Test
	public void 상품없이_상품주문() throws Exception {
		로그인(ADMIN_ID, ADMIN_PW);
		JSONObject dummyProduct = new JSONObject();
		JSONObject requestObject = 주문생성(false, dummyProduct);

		mockMvc.perform(post(PREFIX).content(requestObject.toJSONString()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE).header("Cookie", "session=" + validSession)
				.cookie(new Cookie("session", validSession))).andDo(print()).andExpect(jsonPath("errCode").value(612));
	}

	// TODO JSONObject로 전부 바뀐다음에 7시 넘어서 테스ㅡ를 못했음 addOrder부분 들어가는 위치 전부다 체크해야함
	@Test
	public void 동일상품을_여러개주문() throws Exception {
		로그인(ADMIN_ID, ADMIN_PW);
		JSONObject[] products = new JSONObject[3];
		for (int i = 0; i < 3; i++) {
			products[i] = 상품생성();
		}

		JSONObject requestObject = 주문생성(false, products);

		mockMvc.perform(post(PREFIX).content(requestObject.toJSONString()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE).header("Cookie", "session=" + validSession)
				.cookie(new Cookie("session", validSession))).andExpect(jsonPath("errCode").value(612));
	}

	@Test
	public void 로그인안하고_상품주문() throws Exception {
		JSONObject product = 상품생성();
		JSONObject requestObject = 주문생성(false, product);
		
		mockMvc.perform(post(PREFIX).content(requestObject.toJSONString()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("errCode").value(606));
	}

	@Test
	public void 다른유저의상품취소() throws Exception {
		// 상품은 admin0001(ADMIN_ID)가 하였다.
		로그인(USER_ID, USER_PW);

		mockMvc.perform(delete(PREFIX + "/{reservationID}", TEST_ORDERED_ID).header("Cookie", "session=" + validSession)
				.cookie(new Cookie("session", validSession))).andExpect(jsonPath("errCode").value(613));
	}

	//////////////////////////////////////// TEST_END
	//////////////////////////////////////// ////////////////////////////////////////

	private void 로그인(String id, String pw) throws Exception {
		MvcResult response = mockMvc.perform(post("/user/login").param("userID", id).param("userPW", pw))
				// .andDo(print())
				.andReturn();

		String responseBodstr = response.getResponse().getContentAsString();
		JSONParser parser = new JSONParser();
		JSONObject responseBody = (JSONObject) parser.parse(responseBodstr);
		validSession = responseBody.get("session").toString();
	}

	@SuppressWarnings("unchecked")
	private JSONObject 주문생성(boolean fromBasket, JSONObject... arguments) {
		JSONObject orderObject = new JSONObject();
		JSONArray orderProductList = new JSONArray();
		for (JSONObject argument : arguments) {
			orderProductList.add(argument);
		}

		orderObject.put("timeslotID", TEST_TIMESLOT_ID);
		orderObject.put("fromBasket", fromBasket);
		orderObject.put("productList", orderProductList);

		return orderObject;
	}

	@SuppressWarnings("unchecked")
	private JSONObject 상품생성() {
		JSONObject product = new JSONObject();

		product.put("productID", TEST_PRODUCT_ID);
		product.put("quantity", TEST_PRODUCT_QUANTITY);

		return product;
	}
}
