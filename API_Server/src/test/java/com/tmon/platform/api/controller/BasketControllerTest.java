package com.tmon.platform.api.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

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
import org.springframework.util.StopWatch;

import com.tmon.platform.api.dto.BasketDto;
import com.tmon.platform.api.exception.AbstractCustomException;
import com.tmon.platform.api.exception.AuthException;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.exception.PreConditionException;
import com.tmon.platform.api.exception.RangeNotSatisfyException;
import com.tmon.platform.api.exception.SQLCustomException;
import com.tmon.platform.api.interceptor.LoginCheckInterceptorTest;
import com.tmon.platform.api.service.BasketService;
import com.tmon.platform.api.service.UserService;
import com.tmon.platform.api.util.SessionManager;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
"file:src/test/resources/testServlet-context.xml",
"file:src/test/resources/testDataSource-context.xml",
"file:src/test/resources/testDateFormat-context.xml"})
public class BasketControllerTest {
	
	@Autowired
	private UserService userService;
	@Autowired
	private BasketService basketService;
	
	@Autowired 
	private SessionManager sessionManager;
	@Autowired
	private LoginCheckInterceptorTest loginCheckInterceptorTest;
	
	private static final Logger logger = Logger.getLogger(BasketControllerTest.class);
	
	private static final Object HANDLER = null;
	private static final MockHttpServletRequest REQUEST = new MockHttpServletRequest();
	private static final MockHttpServletResponse RESPONSE = new MockHttpServletResponse();
	
	private static final String USER_ID = "user0001";
	private static final String USER_PW = "user0001";
	
	private static final int PRODUCT_ID = 2;
	private static final int QUANTITY = 2;

	private String session;
	
	@Before
	public void before() throws PreConditionException, AuthException {
		/* 장바구니는 사용자 로그인 후 가능 하므로 로그인과 로그인체크를 선행 */
		loginTest();
		loginInterceptorTest();
	}
	
	@After
	public void after() {
		logger.info("logout 전 - 세션 풀의 user_id : " + sessionManager.getUserId(session));
		userService.logout(session); 
		logger.info("logout 후 - 세션 풀의 user_id : " + sessionManager.getUserId(session));
	}
	
	@Test
	public void loginTest() throws PreConditionException {
		JSONObject obj = userService.login(USER_ID, USER_PW);
		session = obj.get("session").toString();
		
		/* 세션 풀에 세션이 삽입됐는지 체크*/
		assertTrue(sessionManager.isValidSession(session));
		
		/* 
		 * Angular에서 쿠키에 담아서 서버로 전달할 때는 request.getHeader("Cookie")로.......
		 * 자바 에서 쿠키에 담아서 전달할 때는 request.getCookies() 로..........
		 * 따라서 LoginCheckInterceptor를 배포용과 테스트용을 따로 작성해 두었음 
		 * 왜 이렇게만 되는지 고려해 볼 것
		 */
		Cookie cookies = new Cookie("session", session);
		REQUEST.setCookies(cookies);
	}
	
	
	/* 로그인 인터셉터 통과 하는지 체크 */
	@Test
	public void loginInterceptorTest() throws AuthException {
		Boolean handlerTest = loginCheckInterceptorTest.preHandle(REQUEST, RESPONSE, HANDLER);		
		assertEquals(handlerTest, true);
	}
	
	
	/* 장바구니에 상품 추가 */
	@Test
	public void addBasketValidTest() throws RangeNotSatisfyException, NullCustomException, SQLCustomException {
		String user_id = sessionManager.getUserId(session);
		basketService.addBasket(user_id, PRODUCT_ID, QUANTITY);
	}
	
	/* 장바구니에 상품 추가 */
	@Test(expected=AbstractCustomException.class)
	public void addBasketInvalidTest() throws RangeNotSatisfyException, NullCustomException, SQLCustomException {
		String user_id = sessionManager.getUserId(session);
		basketService.addBasket(user_id, 12341234, 123);			// NullCustomException - 없는 상품 번호를 장바구니에 넣으려고할 때 외래키 제약조건 걸림
		//basketService.addBasket(user_id, PRODUCT_ID, 1);			// SQLCustomException - 장바구니에 이미 존재하는 상품 넣을때 PK 제약조건 걸림 
		//asketService.addBasket(user_id, PRODUCT_ID, 123123123);	// RangeNotSatisfyException - 상품수량이 음수이거나 재고수량보다 많을경우 Range에러
		//basketService.addBasket(user_id, PRODUCT_ID, -1);
	}
	
	/* 장바구니에 유효한 상품 삭제 */
	@Test
	public void removeBasketValidTest() throws NullCustomException {
		String user_id = sessionManager.getUserId(session);
		basketService.removeBasket(user_id, PRODUCT_ID);	// 유효한 상품번호 입력 시 정상 동작
	}
	
	/* 장바구니에 유효하지 않은 상품 삭제 */
	@Test(expected=NullCustomException.class)
	public void removeBasketInvalidTest() throws NullCustomException {
		String user_id = sessionManager.getUserId(session);
		basketService.removeBasket(user_id, -1);			// 유효하지 않은 상품번호 입력시 NullCustomException 발생
	}
	
	@Test
	public void getBasketListTest() {
		String user_id = sessionManager.getUserId(session);
		List<BasketDto> list = basketService.basket("user1111");	// 없는 아이디로 바스켓 조회시 빈 객체 리턴
		assertTrue(list.isEmpty());
	}
	
	@Test
	public void incQuantityTest() throws RangeNotSatisfyException, NullCustomException, SQLCustomException {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		String user_id = sessionManager.getUserId(session);
		int preBasketQuantity;
		
		try {
			preBasketQuantity = basketService.getBasketQuantity(user_id, PRODUCT_ID);
		}catch(NullCustomException e) {
			addBasketValidTest();			// 테스트시 필요한 상품이 장바구니에 없을 경우 장바구니에 상품추가  
			preBasketQuantity = basketService.getBasketQuantity(user_id, PRODUCT_ID);
		}
		 
		basketService.incQuantity(user_id, PRODUCT_ID);	 // 상품 수량 증가
		int postBasketQuantity = basketService.getBasketQuantity(user_id, PRODUCT_ID);
		assertEquals(postBasketQuantity, preBasketQuantity + 1);
		
		stopWatch.stop();
		logger.info("incQuantity() 수행시간 : " + stopWatch.getTotalTimeSeconds());
	}
	
	
	@Test
	public void decQuantityTest() throws RangeNotSatisfyException, NullCustomException, SQLCustomException {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		String user_id = sessionManager.getUserId(session);
		int preBasketQuantity;
		
		try {
			preBasketQuantity = basketService.getBasketQuantity(user_id, PRODUCT_ID);
		}catch(NullCustomException e) {
			addBasketValidTest();			// 테스트시 필요한 상품이 장바구니에 없을 경우 장바구니에 상품추가  
			preBasketQuantity = basketService.getBasketQuantity(user_id, PRODUCT_ID);
		}
		
	}
}
