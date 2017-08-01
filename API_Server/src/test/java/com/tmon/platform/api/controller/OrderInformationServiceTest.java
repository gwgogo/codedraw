package com.tmon.platform.api.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


import com.tmon.platform.api.dao.OrderInformationDao;
import com.tmon.platform.api.dto.BasketDto;
import com.tmon.platform.api.dto.OrderInformationDto;
import com.tmon.platform.api.dto.OrderProductDto;
import com.tmon.platform.api.exception.AbstractCustomException;
import com.tmon.platform.api.exception.AuthException;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.exception.PreConditionException;
import com.tmon.platform.api.exception.RangeNotSatisfyException;
import com.tmon.platform.api.exception.SQLCustomException;
import com.tmon.platform.api.interceptor.LoginCheckInterceptorTest;
import com.tmon.platform.api.service.BasketService;
import com.tmon.platform.api.service.OrderInformationService;
import com.tmon.platform.api.service.UserService;
import com.tmon.platform.api.util.SessionManager;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
		"file:src/test/resources/testServlet-context.xml",
		"file:src/test/resources/testDataSource-context.xml", 
		"file:src/test/resources/testDateFormat-context.xml" })
public class OrderInformationServiceTest {

	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private OrderInformationService orderInformationService;	
	@Autowired
	private UserService userService;
	@Autowired
	private BasketService basketService;
	@Autowired
	private LoginCheckInterceptorTest loginCheckInterceptorTest;
	@Autowired
	private OrderInformationDao orderInformationDao;
	
	@Autowired
	private SimpleDateFormat cutOffTimeFormat;
	
	private static final Logger logger = Logger.getLogger(BasketServiceTest.class);
	
	private static final Object HANDLER = null;
	private static final MockHttpServletRequest REQUEST = new MockHttpServletRequest();
	private static final MockHttpServletResponse RESPONSE = new MockHttpServletResponse();
	
	private static final String USER_ID = "user0001";
	private static final String USER_PW = "user0001";

	
	private String session, userID;
	int productID1, productID2, quantity1, quantity2;
	List<Map<String,String>> productList = new ArrayList();	
	
	
	@Before
	public void before() throws AuthException, PreConditionException {
		loginTest();
		userID = sessionManager.getUserId(session);
		loginInterceptorTest();
		
		Map<String, String> productItem1 = new HashMap<>();
		Map<String, String> productItem2 = new HashMap<>();
		
		productID1 = 1;
		quantity1 = 10;
		productID2 = 2;
		quantity2 = 20;
		productItem1.put("product_id", String.valueOf(productID1));		
		productItem1.put("quantity", String.valueOf(quantity1));
		productList.add(productItem1);
		productItem2.put("product_id", String.valueOf(productID2));		
		productItem2.put("quantity", String.valueOf(quantity2));
		productList.add(productItem2);
		
	}
	
	@After
	public void after() {
		
	}
	
	@Test
	public void loginTest() throws PreConditionException {
		JSONObject obj = userService.login(USER_ID, USER_PW);
		session = obj.get("session").toString();
		
		/* 세션 풀에 세션이 삽입됐는지 체크*/
		assertTrue(sessionManager.isValidSession(session));
		Cookie cookies = new Cookie("session", session);
		REQUEST.setCookies(cookies);
		logger.info("=====================로그인 성공=====================");
	}
	
	/* 로그인 인터셉터 통과 하는지 체크 */
	@Test
	public void loginInterceptorTest() throws AuthException  {
		Boolean handlerTest = loginCheckInterceptorTest.preHandle(REQUEST, RESPONSE, HANDLER);		
		assertEquals(handlerTest, true);
		logger.info("=====================인터셉터 통과=====================");
	}
	
	@Test
	public void cutOffTest() {
		int timeslotID = 3;
		Date cutOff = orderInformationDao.getCutOff(timeslotID);
		String strCutOff = cutOffTimeFormat.format(cutOff);
		Date now = new Date();
		String strNow = cutOffTimeFormat.format(now);
		logger.info("strNow : " + strNow );
		logger.info("strCutOff : " + strCutOff );
		if(strNow.compareTo(strCutOff) >= 0) {
			logger.info("cutOff가 지났으므로 주문 불가");
		}
		
	}
	
	@Test
	public void addOrderTest() throws AbstractCustomException, ParseException {
		Map<String, String> orderDto = new HashMap<>();
		int timeslotID = 280;
		orderDto.put("userID", userID);
		orderDto.put("timeslotID", String.valueOf(timeslotID));
		boolean fromBasket = true;
		
		int preTimeslotCount = orderInformationService.getTimeSlotCount(timeslotID);
		orderInformationService.addOrder(orderDto, productList, fromBasket);	// addOrder 호출
		int postTimeslotCount = orderInformationService.getTimeSlotCount(timeslotID);
		assertEquals(postTimeslotCount, preTimeslotCount + 1);					// 주문 후 타임슬롯 count 증가 됐는지 체크
		
		int reservationID = orderInformationDao.getReservationID(orderDto);		// autoincrement 된 reservationID 가져오기
		List<OrderProductDto> orderProductList = orderInformationService.getOrderProductList(reservationID);
		assertEquals(orderProductList.get(0).getProductID(), productID1);		// 주문 후 입력한 상품 및 수량이 제대로 적재 되었는지 체크
		assertEquals(orderProductList.get(0).getQuantity(), quantity1);
		assertEquals(orderProductList.get(1).getProductID(), productID2);
		assertEquals(orderProductList.get(1).getQuantity(), quantity2);
		
		if(fromBasket) {
			List<BasketDto> basketList = basketService.basket(userID);			
			assertTrue(basketList.isEmpty());									// 장바구니 주문이면 장바구니가 초기화 됐는지 체크
		}
	}
	
	@Test
	public void addOrderProductTest() throws SQLCustomException, NullCustomException, RangeNotSatisfyException, ParseException {
		Map<String, String> orderDto = new HashMap<>();
		int timeslotID = 5;
		orderDto.put("userID", userID);
		orderDto.put("timeslotID", String.valueOf(timeslotID));
			
			
		/* autoincrement된 reservationID값을 가져오기 위한 선행작업  */
		int reservationID = orderInformationDao.addOrder(orderDto);	
		
		int preStockQuantity1 = orderInformationService.getStockQuantity(productID1);
		int preStockQuantity2 = orderInformationService.getStockQuantity(productID2);
		orderInformationService.addOrderProduct(reservationID, productList);	// addOrderProduct 호출
		int postStockQuantity1 = orderInformationService.getStockQuantity(productID1);
		int postStockQuantity2 = orderInformationService.getStockQuantity(productID2);
		
		assertEquals(postStockQuantity1, preStockQuantity1 - quantity1);		// 주문내역의 상품 수량 만큼 재고량이 감소했는지 체크
		assertEquals(postStockQuantity2, preStockQuantity2 - quantity2);
		
		List<OrderProductDto> list = orderInformationService.getOrderProductList(reservationID);
		
		assertEquals(list.get(0).getProductID(), productID1);					// 상품 및 수량이 제대로 적재되었는지 체크
		assertEquals(list.get(0).getQuantity(), quantity1);	
		assertEquals(list.get(1).getProductID(), productID2);
		assertEquals(list.get(1).getQuantity(), quantity2);
	}
	
	
	@Test
	public void cancelOrderTest() throws SQLCustomException, NullCustomException, RangeNotSatisfyException, AuthException {
		int reservationID = 113; 
		int timeslotID = orderInformationDao.getTimeSlotId(reservationID);
		
		int preTimeSlotCount = orderInformationService.getTimeSlotCount(timeslotID);
		int preStockQuantity1 = orderInformationService.getStockQuantity(productID1);
		int preStockQuantity2 = orderInformationService.getStockQuantity(productID2);
		orderInformationService.cancelOrder(reservationID, session);
		int postTimeSlotCount = orderInformationService.getTimeSlotCount(timeslotID);
		int postStockQuantity1 = orderInformationService.getStockQuantity(productID1);
		int postStockQuantity2 = orderInformationService.getStockQuantity(productID2);
		
		assertEquals(postTimeSlotCount, preTimeSlotCount - 1);				// 타임슬롯 count가 감소됐는지 체크
		
		assertEquals(postStockQuantity1, preStockQuantity1 + quantity1);	// 주문내역의 상품 수량 만큼 재고량이 증가됐는지 체크
		assertEquals(postStockQuantity2, preStockQuantity2 + quantity2);
		
		OrderInformationDto orderDto = orderInformationDao.getReservationDto(reservationID);
		assertNotNull(orderDto.getCancelDate());		// cancel 됐는지 체크
		assertEquals(orderDto.getStatusID(), 3);		// 상태가 취소로 변경 됐는지 체크
	}
	
	
	@Test
	public void incTimeslotCountTest() throws NullCustomException, RangeNotSatisfyException {
		int timeslotID = 240;
		
		int preCount = orderInformationService.getTimeSlotCount(timeslotID);
		orderInformationService.incCountTimeSlot(timeslotID);
		int postCount = orderInformationService.getTimeSlotCount(timeslotID);
		assertEquals(postCount, preCount + 1);
	}
	
	
	@Test
	public void incSellQuantityTest() throws NullCustomException, RangeNotSatisfyException {
		int preStockQuantity = orderInformationService.getStockQuantity(productID1);
		orderInformationService.incSellQuantity(productList);
		int postStockQuantity = orderInformationService.getStockQuantity(productID1);
		assertEquals(postStockQuantity, preStockQuantity - quantity1);
		
		preStockQuantity = orderInformationService.getStockQuantity(productID2);
		orderInformationService.incSellQuantity(productList);
		postStockQuantity = orderInformationService.getStockQuantity(productID2);
		assertEquals(postStockQuantity, preStockQuantity - quantity2);
	}
	
	
	@Test
	public void decSellQuantityTest() throws NullCustomException {
		int reservationID = 3;
		int preStockQuantity = orderInformationService.getStockQuantity(productID1);
		orderInformationService.decSellQuantity(reservationID);
		int postStockQuantity = orderInformationService.getStockQuantity(productID1);
		assertEquals(postStockQuantity, preStockQuantity + quantity1);
		
		preStockQuantity = orderInformationService.getStockQuantity(productID2);
		orderInformationService.decSellQuantity(reservationID);
		postStockQuantity = orderInformationService.getStockQuantity(productID2);
		assertEquals(postStockQuantity, preStockQuantity + quantity2);
	}
	

}
