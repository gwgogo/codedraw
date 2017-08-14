package com.tmon.platform.api.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.tmon.platform.api.dao.OrderInformationDao;
import com.tmon.platform.api.dto.UserDto;
import com.tmon.platform.api.exception.AuthException;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.exception.PreConditionException;
import com.tmon.platform.api.exception.RangeNotSatisfyException;
import com.tmon.platform.api.exception.SQLCustomException;
import com.tmon.platform.api.util.SessionManager;



/**
 * @author 신광원
 * @description 주문 취소에 관한 Test Code
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
"file:src/test/resources/testServlet-context.xml",
"file:src/test/resources/testDataSource-context.xml"})
public class CancelOrderServiceTest {

	@Autowired
	OrderInformationService orderInformationService;
	
	@Autowired
	OrderInformationDao orderInformationDao;
	
	@Autowired
	UserService userService;
	
	@Autowired
	SessionManager sessionManager;
	
	private static final int PRODUCT_ID1 = 1;
	private static final int QUANTITY1 = 10;
	private static final int TIMESLOT_ID = 352;
	private String session;
	int testReservationID;
	
	
	@Before
	public void before() throws PreConditionException, RangeNotSatisfyException, SQLCustomException, NullCustomException {
		loginTest();
			
		List<Map<String, String>> productList = new ArrayList<>();
		Map<String, String> productItem1 = new HashMap<>();
		
		productItem1.put("productID", String.valueOf(PRODUCT_ID1));
		productItem1.put("quantity", String.valueOf(QUANTITY1));
		productList.add(productItem1);
		
		
		Map<String, String> orderDto = new HashMap<>();
		orderDto.put("userID", "user0001");
		orderDto.put("timeslotID", String.valueOf(TIMESLOT_ID));
		
		boolean fromBasket=true;
		
		
		testReservationID = orderInformationService.addOrder(orderDto, productList, fromBasket);
		
	}
	
	@After
	public void after() throws NullCustomException, RangeNotSatisfyException {
		
		orderInformationService.decCountTimeSlot(TIMESLOT_ID);
		orderInformationDao.decSellQuantity(PRODUCT_ID1, QUANTITY1);
		
		userService.logout(session);
	}
	
	@Test 
	public void loginTest() throws PreConditionException {
		JSONObject obj = userService.login("user0001", "user0001");
		session = obj.get("session").toString();
		assertTrue(sessionManager.isValidSession(session));
	}
	
	
	@Test
	public void 정상적인취소()
			throws SQLCustomException, NullCustomException, RangeNotSatisfyException, AuthException {

		
		int preTimeSlotCount = orderInformationDao.getTimeSlotCount(TIMESLOT_ID);
		int preStockQuantity1 = orderInformationDao.getStockQuantity(PRODUCT_ID1);
		orderInformationService.cancelOrder(testReservationID, session);
		
		int postTimeSlotCount = orderInformationDao.getTimeSlotCount(TIMESLOT_ID);
		int postStockQuantity1 = orderInformationDao.getStockQuantity(PRODUCT_ID1);

		assertEquals(postTimeSlotCount, preTimeSlotCount - 1); // 타임슬롯 count가 감소됐는지 체크

		assertEquals(postStockQuantity1, preStockQuantity1 + QUANTITY1); // 주문내역의 상품 수량 만큼 재고량이 증가됐는지 체크

		orderInformationService.incCountTimeSlot(TIMESLOT_ID);	// After에서 또 한번 감소시키기 때문에 주문 취소성공하면 증가시켜둠
		orderInformationDao.incSellQuantity(PRODUCT_ID1, QUANTITY1);
	}
	
	@Test
	public void 유효하지않은주문ID로주문취소() throws SQLCustomException, NullCustomException, AuthException, RangeNotSatisfyException {
		int invalidReservationID = -1;
		int preStockQuantity = orderInformationDao.getStockQuantity(PRODUCT_ID1);
		try {
			orderInformationService.cancelOrder(invalidReservationID, session);
		}catch(NullCustomException e) {
			int postStockQuantity = orderInformationDao.getStockQuantity(PRODUCT_ID1);
			assertEquals(postStockQuantity, preStockQuantity);
		}
	}
	
	
	@Test
	public void 다른사용자의주문취소() throws SQLCustomException, NullCustomException, RangeNotSatisfyException, AuthException {
		UserDto userDto = new UserDto();
		userDto.setUserID("user0002");
		String session = sessionManager.createSession(userDto);	// Before에서는 user0001로 주문을 생성했기에 다른 사용자로 세션을 바꾸어 주문 취소 테스트
		int preStockQuantity = orderInformationDao.getStockQuantity(PRODUCT_ID1);
		try {
			orderInformationService.cancelOrder(testReservationID, session);
		}catch(AuthException e) {
			int postStockQuantity = orderInformationDao.getStockQuantity(PRODUCT_ID1);
			assertEquals(postStockQuantity, preStockQuantity);
		}
	} 
	
}
