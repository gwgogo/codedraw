package com.tmon.platform.api.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.StopWatch;

import com.tmon.platform.api.dao.BasketDao;
import com.tmon.platform.api.dto.BasketDto;
import com.tmon.platform.api.exception.AuthException;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.exception.PreConditionException;
import com.tmon.platform.api.exception.RangeNotSatisfyException;
import com.tmon.platform.api.exception.SQLCustomException;
import com.tmon.platform.api.util.SessionManager;


/**
 * @author 신광원
 * @description 장바구니 관련 Test Code
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
"file:src/test/resources/testServlet-context.xml",
"file:src/test/resources/testDataSource-context.xml"})
public class BasketServiceTest {
	
	@Autowired
	private UserService userService;
	@Autowired
	private BasketService basketService;
	@Autowired
	private BasketDao basketDao;
	
	@Autowired 
	private SessionManager sessionManager;
	
	private static final Logger logger = Logger.getLogger(BasketServiceTest.class);
	
	private static final String USER_ID = "user0001";
	private static final String USER_PW = "user0001";
	
	private static final int PRODUCT_ID = 3;
	private static final int QUANTITY = 3;

	private String session;
	private String userID;
	
	@Before
	public void before() throws PreConditionException, AuthException {
		/* 장바구니는 사용자 로그인 후 가능 하므로 로그인과 로그인체크를 선행 */
		loginTest();
		userID = sessionManager.getUserId(session);
		
	}
	
	@After
	public void after() throws NullCustomException {
		userService.logout(session); 
	}
	
	@Test
	public void loginTest() throws PreConditionException {
		JSONObject obj = userService.login(USER_ID, USER_PW);
		session = obj.get("session").toString();
		/* 세션 풀에 세션이 삽입됐는지 체크*/
		assertTrue(sessionManager.isValidSession(session));	
	}
	
	@Test
	public void 정상적으로장바구니에상품추가() throws RangeNotSatisfyException, NullCustomException, SQLCustomException {
		basketService.addBasket(userID, PRODUCT_ID, QUANTITY);
		List<BasketDto> list = basketService.basket(userID);
		assertFalse(list.isEmpty());
		assertEquals(list.get(0).getUserID(), userID);
		basketService.removeBasket(userID, PRODUCT_ID);			// 디비 원래상태 복구
	}
	
	
	@Test(expected=NullCustomException.class)
	public void 없는상품을장바구니에추가() throws RangeNotSatisfyException, NullCustomException, SQLCustomException {
		//basketService.addBasket(userID, 12341234, 123);			// NullCustomException - 없는 상품 번호를 장바구니에 넣으려고할 때 외래키 제약조건 걸림
		basketService.addBasket(userID, -1 , 123);					// NullCustomException - 없는 상품 번호를 장바구니에 넣으려고할 때 외래키 제약조건 걸림
	}
	
	
	@Test
	public void 장바구니에존재하는상품을장바구니에추가() throws RangeNotSatisfyException, NullCustomException, SQLCustomException {
		basketService.addBasket(userID, PRODUCT_ID, QUANTITY);
		int quantity = 0;
		try {
			basketService.addBasket(userID, PRODUCT_ID, 1);			// SQLCustomException - 장바구니에 이미 존재하는 상품 넣을때 PK 제약조건 걸림
		}catch(SQLCustomException e) {
			quantity = basketDao.getBasketQuantity(userID, PRODUCT_ID);
		}
		assertEquals(quantity,  QUANTITY);	// catch에 걸려야(=에러가 발생해야만) 테스트 성공
		 basketService.removeBasket(userID, PRODUCT_ID);
	}
	
	
	@Test(expected=RangeNotSatisfyException.class)
	public void 재고량보다많은수량을장바구니에추가() throws RangeNotSatisfyException, NullCustomException, SQLCustomException {
		basketService.addBasket(userID, PRODUCT_ID, 123123123);	// RangeNotSatisfyException - 상품수량이 음수이거나 재고수량보다 많을경우 Range에러
		//basketService.addBasket(userID, PRODUCT_ID, -1);
	}
	
	
	/* 장바구니에 유효하지 않은 상품 삭제 */
	@Test(expected=NullCustomException.class)
	public void removeBasketInvalidTest() throws NullCustomException {
		String userID = sessionManager.getUserId(session);
		basketService.removeBasket(userID, -1);					// 유효하지 않은 상품번호 입력시 NullCustomException 발생
	}
	
	
	@Test
	public void 로그인하지않은아이디로장바구니조회() {
		//String userID = sessionManager.getUserId(session);
		List<BasketDto> list = basketService.basket("user1111");	// 없는 아이디로 바스켓 조회시 빈 객체 리턴
		assertTrue(list.isEmpty());
	}
	
	
	@Test
	public void 장바구니상품수량증가테스트() throws RangeNotSatisfyException, NullCustomException, SQLCustomException {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		basketService.addBasket(userID, PRODUCT_ID, QUANTITY);
		int preBasketQuantity = basketDao.getBasketQuantity(userID, PRODUCT_ID);
		 
		basketService.incQuantity(userID, PRODUCT_ID);		
		int postBasketQuantity = basketDao.getBasketQuantity(userID, PRODUCT_ID);
		assertEquals(postBasketQuantity, preBasketQuantity + 1);
		
		stopWatch.stop();
		logger.info("incQuantity() 수행시간 : " + stopWatch.getTotalTimeSeconds());
		basketService.removeBasket(userID, PRODUCT_ID);		// 디비 원래상태 복구
	}

	
	@Test
	public void 장바구니상품감소테스트() throws RangeNotSatisfyException, NullCustomException, SQLCustomException {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		basketService.addBasket(userID, PRODUCT_ID, QUANTITY);
		int preBasketQuantity = basketDao.getBasketQuantity(userID, PRODUCT_ID);;
		
		basketService.decQuantity(userID, PRODUCT_ID);
		int postBasketQuantity = basketDao.getBasketQuantity(userID, PRODUCT_ID);
		assertEquals(postBasketQuantity,  preBasketQuantity - 1);
		
		stopWatch.stop();
		logger.info("decQuantity() 수행시간 : " + stopWatch.getTotalTimeSeconds());
		basketService.removeBasket(userID, PRODUCT_ID);					// 디비 원래상태 복구
	}
	
	
	@Test
	public void 장바구니테스트에러() throws RangeNotSatisfyException, NullCustomException, SQLCustomException {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		int basketQuantity = 0;
		int productMaxQuantity = basketDao.getMaxQuantity(PRODUCT_ID);
		basketService.addBasket(userID, PRODUCT_ID, productMaxQuantity);	// 최대수량만큼 장바구니에 셋팅
		
		try {
			basketService.incQuantity(userID, PRODUCT_ID);
		}catch(RangeNotSatisfyException e) {
			basketQuantity = basketDao.getBasketQuantity(userID, PRODUCT_ID);
		}
		
		assertEquals(basketQuantity, productMaxQuantity);
		stopWatch.stop();
		logger.info("incQuantity() 수행시간 : " + stopWatch.getTotalTimeSeconds());
		basketService.removeBasket(userID, PRODUCT_ID);		// 디비 원래상태 유지
	}
	
}
