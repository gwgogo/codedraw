package com.tmon.platform.api.service;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
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

import com.tmon.platform.api.dao.BasketDao;
import com.tmon.platform.api.dao.OrderInformationDao;
import com.tmon.platform.api.dto.BasketDto;
import com.tmon.platform.api.dto.OrderInformationDto;
import com.tmon.platform.api.exception.AbstractCustomException;
import com.tmon.platform.api.exception.AuthException;
import com.tmon.platform.api.exception.DateFormatException;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.exception.PreConditionException;
import com.tmon.platform.api.exception.RangeNotSatisfyException;
import com.tmon.platform.api.exception.SQLCustomException;
import com.tmon.platform.api.util.SessionManager;



/**
 * @author 신광원
 * @description 주문 조회 및 적재 등에 관한 TestCode
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { 
		"file:src/test/resources/testServlet-context.xml",
		"file:src/test/resources/testDataSource-context.xml" })
public class OrderInformationServiceTest {
	

	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private OrderInformationService orderInformationService;
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderInformationDao orderInformationDao;
	@Autowired
	private BasketDao basketDao;

	private static final String VALID_USER_ID = "user0001";
	private static final String VALID_USER_PW = "user0001";

	private static final int VALID_TIMESLOT_ID = 352;
	private static final int INVALID_TIMESLOT_ID = 9999;
	
	private static final int PRODUCT_ID1 = 1;
	private static final int QUANTITY1 = 10;


	
	List<Map<String, String>> productList = new ArrayList<>();
	Map<String, String> orderDto = new HashMap<>();
	boolean fromBasket = true;
	String session;
	int testReservationID;
	@Before
	public void before() throws AuthException, PreConditionException {
		loginTest();

		orderDto.put("userID", VALID_USER_ID);					
		orderDto.put("timeslotID", String.valueOf(VALID_TIMESLOT_ID));
		
		Map<String, String> productItem1 = new HashMap<>();
		productItem1.put("productID", String.valueOf(PRODUCT_ID1));
		productItem1.put("quantity", String.valueOf(QUANTITY1));
		productList.add(productItem1);
		
	}

	@After
	public void after() {
		userService.logout(session);
		
	}


	@Test
	public void loginTest() throws PreConditionException{
		JSONObject obj = userService.login(VALID_USER_ID, VALID_USER_PW);
		session = obj.get("session").toString();

		/* 세션 풀에 세션이 삽입됐는지 체크 */
		assertTrue(sessionManager.isValidSession(session));
	}


	@Test
	public void 사용자별주문내역조회() throws DateFormatException, SQLCustomException, RangeNotSatisfyException, NullCustomException {
		int testReservationID = orderInformationService.addOrder(orderDto, productList, fromBasket);	// 테스트할 유저로 임의의 주문을 넣어 둠
		
		String searchInitDate = "2017-07-01";
		String searchFinishDate = "2017-08-20";
		
		List<OrderInformationDto> orderList = orderInformationService.selectByDateAndUser(searchInitDate, searchFinishDate, VALID_USER_ID);
		
		assertEquals(orderList.get(0).getReservationID(), testReservationID);	// 방금 주문한 주문번호와 조회한 주문번호가 같은지 체크
		assertEquals(orderList.get(0).getUserID(), VALID_USER_ID);
		assertEquals(orderList.get(0).getStatusID(), 0);
		
		
		/* 디비 원래상태로 변경 */
		orderInformationDao.decSellQuantity(PRODUCT_ID1,QUANTITY1);
		orderInformationService.decCountTimeSlot(VALID_TIMESLOT_ID);
			
	}
	
	@Test
	public void 주문조회시InitDate가더클때예외() throws SQLCustomException, RangeNotSatisfyException, NullCustomException {
		int testReservationID = orderInformationService.addOrder(orderDto, productList, fromBasket);	// 테스트할 유저로 임의의 주문을 넣어 둠
		String searchInitDate = "2017-08-30";
		String searchFinishDate = "2017-07-30";
		List<OrderInformationDto> orderList1 = null;
		List<OrderInformationDto> orderList2 = null;
		
		try {
			orderList1 = orderInformationService.selectByDate(searchInitDate, searchFinishDate);
			orderList2 = orderInformationService.selectByDateAndUser(searchInitDate, searchFinishDate, VALID_USER_ID);
		}catch(DateFormatException e) {
			orderInformationDao.decSellQuantity(PRODUCT_ID1,QUANTITY1);
			orderInformationService.decCountTimeSlot(VALID_TIMESLOT_ID);
		}
		
		assertNull(orderList1);		// try에서 서비스가 실행되었더라면 null이  아닌, 값 또는 empty가 될 것
		assertNull(orderList2);
	}
	
	@Test
	public void 주문조회시날짜포맷입력예외() throws RangeNotSatisfyException, SQLCustomException, NullCustomException, DateFormatException {
		
		String searchInitDate = "2017.07.30";
		String searchFinishDate = "2017-08-05";
		List<OrderInformationDto> orderList = null;
		try {
			orderList = orderInformationService.selectByDate(searchInitDate, searchFinishDate);
		}catch(DateFormatException e) {
			assertNull(orderList);		
		}
	}
	
	@Test
	public void 관리자가모든주문내역조회() throws DateFormatException, SQLCustomException, RangeNotSatisfyException, NullCustomException {
		testReservationID = orderInformationService.addOrder(orderDto, productList, fromBasket);	// 테스트할 유저로 임의의 주문을 넣어 둠
		
		String searchInitDate = "2017-07-30";
		String searchFinishDate = "2017-08-30";
		
		List<OrderInformationDto> orderList = orderInformationService.selectByDate(searchInitDate, searchFinishDate);
		assertThat(orderList.get(0).getReservationID(), notNullValue());
		
		orderInformationDao.decSellQuantity(PRODUCT_ID1,QUANTITY1);
		orderInformationService.decCountTimeSlot(VALID_TIMESLOT_ID);
	}
	
	
	@Test(expected = NullCustomException.class)
	public void 유효하지않은타임슬롯의주문() throws RangeNotSatisfyException, SQLCustomException, NullCustomException {
		orderDto.put("timeslotID", String.valueOf(INVALID_TIMESLOT_ID));		// 유효하지 않은 timeslot으로 셋팅
		orderInformationService.addOrder(orderDto, productList, fromBasket);	// cutOff체크시 NullCustomException 발생
	}
	
	
	@Test(expected = SQLCustomException.class)
	public void 유효하지않은사용자의주문() throws RangeNotSatisfyException, SQLCustomException, NullCustomException {
		orderDto.put("userID", "invalidUser");									// 유효하지 않은 사용자로 셋팅
		orderInformationService.addOrder(orderDto, productList, fromBasket);	// insert시 SQLCustomException 발생 - 존재하지 않은 userID를 참조하기 때문
	}

	
	@Test
	public void 정상적인주문() throws AbstractCustomException {

		int preTimeslotCount = orderInformationDao.getTimeSlotCount(VALID_TIMESLOT_ID);
		int preStockQuantity = orderInformationDao.getStockQuantity(PRODUCT_ID1);
		int testReservationID = orderInformationService.addOrder(orderDto, productList, fromBasket); // addOrder 테스트

		int postTimeslotCount = orderInformationDao.getTimeSlotCount(VALID_TIMESLOT_ID);
		assertEquals(postTimeslotCount, preTimeslotCount + 1); 			// 주문 후 타임슬롯 count 증가 됐는지 체크

		int postStockQuantity = orderInformationDao.getStockQuantity(PRODUCT_ID1);
		assertEquals(postStockQuantity, preStockQuantity - QUANTITY1);

		List<BasketDto> postBasketList = basketDao.basket(VALID_USER_ID);
		assertTrue(postBasketList.isEmpty()); 	// 장바구니 주문이면 장바구니가 초기화 됐는지 체크
		
		
		/* 주문완료후 디비 원래상태로 변경 */
		orderInformationDao.decSellQuantity(PRODUCT_ID1,QUANTITY1);
		orderInformationService.decCountTimeSlot(VALID_TIMESLOT_ID);
	}
	
	@Test
	public void 타임슬롯제로에서감소하려는테스트() throws NullCustomException, RangeNotSatisfyException {
		int decPreCount = orderInformationDao.getTimeSlotCount(VALID_TIMESLOT_ID); 
		
		for(int i = 0; i < decPreCount; i++) {	
			orderInformationService.decCountTimeSlot(VALID_TIMESLOT_ID);	// 해당 타임슬롯의 Count를 0으로 세팅
		}
		try {
			orderInformationService.decCountTimeSlot(VALID_TIMESLOT_ID);	// 여기서 예외발생시킴
		}catch(RangeNotSatisfyException e) {
			int count = orderInformationDao.getTimeSlotCount(VALID_TIMESLOT_ID);
			assertEquals(count, 0);
		}
		
		for(int i = 0; i < decPreCount; i++) {
			orderInformationService.incCountTimeSlot(VALID_TIMESLOT_ID);	// 타임슬롯 count 원상복구
		}
	}
	
	
	@Test
	public void 타임슬롯꽉찬상태에서증가하려는테스트() throws NullCustomException, RangeNotSatisfyException {
		
		int incPreCount = orderInformationDao.getTimeSlotCount(VALID_TIMESLOT_ID);	
		int incTmpCount = 5 - incPreCount;
		
		for(int i = 0; i < incTmpCount; i++) {	
			orderInformationService.incCountTimeSlot(VALID_TIMESLOT_ID);	// 해당 타임슬롯의 Count를 5로 세팅
		}
		
		try {
			orderInformationService.incCountTimeSlot(VALID_TIMESLOT_ID);	// 여기서 예외발생시킴
		}catch(RangeNotSatisfyException e) {
			int count = orderInformationDao.getTimeSlotCount(VALID_TIMESLOT_ID);
			assertEquals(count, 5);
		}
		
		for(int i = 0; i < incTmpCount; i++) {
			orderInformationService.decCountTimeSlot(VALID_TIMESLOT_ID);	// 타임슬롯 count 원상복구
		}
	}
	
	
	@Test(expected = RangeNotSatisfyException.class)
	public void 재고량보다많은상품을주문하는테스트() throws NullCustomException, RangeNotSatisfyException, SQLCustomException {
		
		int maxQuantity = basketDao.getMaxQuantity(PRODUCT_ID1);//재고량 가져옴
		
		List<Map<String, String>> productList = new ArrayList<>();
		Map<String, String> product = new HashMap<>();
		product.put("productID", String.valueOf(PRODUCT_ID1));
		product.put("quantity", String.valueOf(maxQuantity + 100));
		productList.add(product);
		orderInformationService.incSellQuantity(productList);				// 예외 발생
	}
	
}
