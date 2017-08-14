package com.tmon.platform.api.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmon.platform.api.dao.OrderInformationDao;
import com.tmon.platform.api.dto.OrderInformationDto;
import com.tmon.platform.api.dto.OrderProductDto;
import com.tmon.platform.api.exception.AuthException;
import com.tmon.platform.api.exception.DateFormatException;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.exception.RangeNotSatisfyException;
import com.tmon.platform.api.exception.SQLCustomException;
import com.tmon.platform.api.service.BasketService;
import com.tmon.platform.api.service.OrderInformationService;
import com.tmon.platform.api.service.UserService;
import com.tmon.platform.api.util.SessionManager;
import com.tmon.platform.api.util.UtilForDate;

@Service
public class OrderInformationServiceImpl implements OrderInformationService {

	private static final Logger logger = LoggerFactory.getLogger(OrderInformationServiceImpl.class);

	private static final int ORDER_COMPLETE_STATUS = 0;
	private static final int MAX_TIMESLOT_COUNT = 5;
	private static final int ADMIN_PRIVILEGE = 1;
	private static final long TIME_GAP = 32400000;

	@Autowired
	private OrderInformationDao orderInformationDao;

	@Autowired
	private BasketService basketService;

	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private UserService userService;

	@Override
	public List<OrderInformationDto> selectByDate(String searchInitDate, String searchFinishDate)
			throws DateFormatException, SQLCustomException {

		// WHERE절 변수 입력을 위해 parameterType을 Map구조로 한다.
		Map<String, Object> parameters = new HashMap<String, Object>();

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			parameters.put("searchInitDate", dateFormat.parse(searchInitDate));
			parameters.put("searchFinishDate", dateFormat.parse(searchFinishDate));
		} catch (ParseException e) {
			logger.info("Erro at OrderInformation selectByDate");
			throw new DateFormatException(616, "Incorrect input Date data");
		}
		
		/* 검색 시작 날짜가 검색 끝 날짜 보다 작아야 한다. */
		validateDate(parameters);

		try {
			// Dao에서는 Map객체를 parameter로 받는다.
			return orderInformationDao.selectByDate(parameters);
		} catch (Exception e) {
			// OrderInformation Select 실패
			throw new SQLCustomException(615, "Fail Select ORDER_INFORMATION By Date Error");
		}

	}

	@Override
	public List<OrderInformationDto> selectByDateAndUser(String searchInitDate, String searchFinishDate, String userID)
			throws DateFormatException, SQLCustomException, NullCustomException {
		
		// WHERE절 변수 입력을 위해 parameterType을 Map구조로 한다.
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			parameters.put("searchInitDate", dateFormat.parse(searchInitDate));
			parameters.put("searchFinishDate", dateFormat.parse(searchFinishDate));
			parameters.put("userID", userID);

		} catch (ParseException e) {
			logger.info("Erro at OrderInformation selectByDate");
			throw new DateFormatException(616, "Incorrect input Date data");
		}

		/* 검색 시작 날짜가 검색 끝 날짜 보다 작아야 한다. */
		validateDate(parameters);
		
		/* 존재하는 회원인지 아닌지 구분한다.*/
		userService.userValid(userID);

		try {
			// Dao에서는 Map객체를 parameter로 받는다.
			return orderInformationDao.selectByDateAndUser(parameters);
		} catch (Exception e) {
			// OrderInformation Select 실패
			throw new SQLCustomException(615, "Fail Select ORDER_INFORMATION By Date And User Error");
		}
	}

	/* Service로직을 수행할지 안할지 결정하는 메소드 */
	private void validateDate(Map<String, Object> parameters) throws DateFormatException {
		
		/* 검색 시작 날짜가 검색 끝 날짜 보다 작아야 한다. */
		long initDate = ((Date) parameters.get("searchInitDate")).getTime();
		long finishDate = ((Date) parameters.get("searchFinishDate")).getTime();
		
		int flag = UtilForDate.compareDate(initDate, finishDate);
		if (flag < 0) {
			logger.info("searchInitDate: " + parameters.get("searchInitDate"));
			logger.info("searchFinishDate: " + parameters.get("searchFinishDate"));
			throw new DateFormatException(616, "searchInitDate must be smaller than searchFinishDate");
		}
	}

 
	/**
	 * @author 신광원
	 * @description 주문 현황 적재  (주문 현황 적재, 주문 내역(상품목록)적재, 타임슬롯count증가, 상품 재고량 감소, 장바구니 초기화)
	 */
	@Transactional(rollbackFor= {RangeNotSatisfyException.class, SQLCustomException.class, NullCustomException.class})
	public int addOrder(Map<String, String> orderDto, List<Map<String, String>> productList, boolean fromBasket) 
			throws RangeNotSatisfyException, NullCustomException, SQLCustomException {
		int reservationID;
		int timeslotID = Integer.parseInt(orderDto.get("timeslotID"));
		
		validateCutOffTime(timeslotID);
		try {
			reservationID = orderInformationDao.addOrder(orderDto); // RESERVATION_ORDER 테이블에 주문 현황 적재
		} catch (Exception e) {
			throw new SQLCustomException(611, "Fail : Insert Order SQL Error");
		}
		addOrderProduct(reservationID, productList); // ORDER_INFORMATION 테이블에 주문상품 내역 적재
		incCountTimeSlot(timeslotID); // 해당 TIMESLOT의 count 증가
		if (fromBasket) { // 장바구니 전체 주문일 경우에만 장바구니 초기화
			basketService.cleanBasket(orderDto.get("userID"));
		}
		return reservationID;
	}

	
	/**
	 * @author 신광원
	 * @description 주문 내역에 있는 상품리스트 적재  
	 */
	@Transactional(rollbackFor= {RangeNotSatisfyException.class, SQLCustomException.class, NullCustomException.class})
	public void addOrderProduct(int reservationID, List<Map<String, String>> productList)
			throws SQLCustomException, NullCustomException, RangeNotSatisfyException {
		incSellQuantity(productList); // 상품 판매량 증가
		try {
			orderInformationDao.addOrderProduct(reservationID, productList); // 주문 내역의 상품 리스트 적재
		} catch (Exception e) {
			throw new SQLCustomException(612, "Fail : Insert OrderProduct SQL Error");
		}
	}

	
	/**
	 * @author 신광원
	 * @description 주문 취소 (status변경 , 타임슬롯 count 감소, 상품재고량 원상복구)
	 */
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor= RangeNotSatisfyException.class)
	public JSONObject cancelOrder(int reservationID, String session) throws RangeNotSatisfyException, AuthException, NullCustomException {

		Map<String, Integer> timeSlotAndStatus = getTimeSlotAndStatus(reservationID);
		Integer statusID = timeSlotAndStatus.get("statusID");
		Integer timeslotID = timeSlotAndStatus.get("timeslotID");

		if (ORDER_COMPLETE_STATUS != statusID) {		// 주문 완료 status가 아닌데 주문 취소 행위
			throw new RangeNotSatisfyException(615, "Can't cancel : OrderStatus is not ORDER_COMPLETE");
		}

		validateAuthority(reservationID, session);		// 주문취소 권한체크 (관리자는 모두 가능, 사용자는 자신의 것만 가능)
		validateCutOffTime(timeslotID);					// cutOff 체크
		orderInformationDao.cancelOrder(reservationID); // 주문취소 상태로 변경
		decCountTimeSlot(timeslotID);					// 타임슬롯 count 감소
		decSellQuantity(reservationID);					// 주문 취소시 상품 판매 수량 원상복구

		JSONObject obj = new JSONObject();
		obj.put("msg", "Success Update Reservation");
		return obj;
	}

	
	
	/**
	 * @author 신광원
	 * @description 주문 완료시 해당하는 타임슬롯 카운트 증가  
	 */
	@Transactional(rollbackFor= {RangeNotSatisfyException.class, SQLCustomException.class, NullCustomException.class})
	public void incCountTimeSlot(int timeslotID) throws NullCustomException, RangeNotSatisfyException  {
		int count = getTimeSlotCount(timeslotID);
		if (MAX_TIMESLOT_COUNT <= count ) {
			throw new RangeNotSatisfyException(616, "Can't increse TimeslotCount : Timeslot Full Count");
		}
		orderInformationDao.incCountTimeSlot(timeslotID);
	}

	
	
	/**
	 * @author 신광원
	 * @description 주문 취소시 해당하는 타임슬롯 카운트 감소  
	 */
	@Transactional(rollbackFor= RangeNotSatisfyException.class)
	public void decCountTimeSlot(int timeslotID) throws NullCustomException, RangeNotSatisfyException {
		int count = getTimeSlotCount(timeslotID);
		if (count <= 0) {
			throw new RangeNotSatisfyException(616, "Can't decrese TimeslotCount : Now, Timeslot Count is Zero");
		}
		orderInformationDao.decCountTimeSlot(timeslotID);
	}

	
	/**
	 * @author 신광원
	 * @description 타임슬롯에 해당하는 타임슬롯count 가져오기  
	 */
	private int getTimeSlotCount(int timeslotID) throws NullCustomException {
		int count = orderInformationDao.getTimeSlotCount(timeslotID);
		try {
			count = orderInformationDao.getTimeSlotCount(timeslotID);
		} catch (NullPointerException e) {
			throw new NullCustomException(614, "Invalid timeslotID in TIMESLOT");
		}
		return count;
	}
 
	
	/**
	 * @author 신광원
	 * @description 주문 완료시 상품 구매 수량 만큼 sell_quantity 증가  
	 */
	@Transactional(rollbackFor= {RangeNotSatisfyException.class, SQLCustomException.class, NullCustomException.class})
	public void incSellQuantity(List<Map<String, String>> productList) throws NullCustomException, RangeNotSatisfyException {
		
		for(int idx = 0; idx < productList.size(); idx ++) {
			if(productList.get(idx).get("productID") == null || productList.get(idx).get("quantity") == null) {
				throw new NullCustomException(625, "incSellQuantity : Invalid productID or Quantity");
			}
			int productID = Integer.parseInt(productList.get(idx).get("productID"));
			int quantity = Integer.parseInt(productList.get(idx).get("quantity"));
			int stockQuantity = getStockQuantity(productID);
			if (stockQuantity < quantity) {
				throw new RangeNotSatisfyException(615, "incSellQuantity : stockQuantity less than orderQuantity");
			}
			orderInformationDao.incSellQuantity(productID, quantity);
		}
	}

	
	/**
	 * @author 신광원
	 * @description 주문 취소시 상품 구매 수량 만큼 sell_quantity 감소  
	 */
	@Transactional(rollbackFor= RangeNotSatisfyException.class)
	public void decSellQuantity(int reservationID) throws NullCustomException {
		List<OrderProductDto> orderProductList = getOrderProductList(reservationID);
		for (int idx = 0; idx < orderProductList.size(); idx++) {
			int productID = orderProductList.get(idx).getProductID();
			int quantity = orderProductList.get(idx).getQuantity();
			orderInformationDao.decSellQuantity(productID, quantity);
		}
	}


	/**
	 * @author 신광원
	 * @description 상품의 재고량 가져오기 (max_quantity - sell_quantity)
	 */
	private int getStockQuantity(int productID) throws NullCustomException {
		int stockQuantity;
		try {
			stockQuantity = orderInformationDao.getStockQuantity(productID);
		} catch (NullPointerException e) {
			throw new NullCustomException(614, "getStockQuantity : Invalid Product ID");
		}
		return stockQuantity;
	}

	
	/**
	 * @author 신광원
	 * @description 주문에 해당하는 상품 내역 가져오기
	 */
	private List<OrderProductDto> getOrderProductList(int reservationID) throws NullCustomException {
		List<OrderProductDto> list = orderInformationDao.getOrderProductList(reservationID);
		if (list.isEmpty()) {
			throw new NullCustomException(614, "Invalid reservationID in ORDER_INFORMATION");
		}
		return list;
	}

	
	/**
	 * @author 신광원
	 * @description 주문에 해당하는 타임슬롯ID와 현재 주문상태 가져오기
	 */
	private Map<String, Integer> getTimeSlotAndStatus(int reservationID) throws NullCustomException {
		Map<String, Integer> map = orderInformationDao.getTimeSlotAndStatus(reservationID);
		if (map == null) {
			throw new NullCustomException(614, "Invalid reservationID in RESERVATION_ORDER");
		}
		return map;
	}

	
	/**
	 * @author 신광원
	 * @description 주문에 해당하는 userID 가져오기 - 주문과 일치하지 않은 USER가 주문번호를 통해 주문을 취소하려는 경우를 막기 위해 사용
	 */
	private String getUserId(int reservationID) throws NullCustomException {
		String userID = orderInformationDao.getUserId(reservationID);
		if (userID == null) {
			throw new NullCustomException(614, "Invalid reservationID in RESERVATION_ORDER");
		}
		return userID;
	}

	
	/**
	 * @author 신광원
	 * @description 현재 시간이 CutOff 시간을 지나지 않았는지 확인 
	 */
	private void validateCutOffTime(int timeslotID) throws RangeNotSatisfyException, NullCustomException {
		Long cutOff;
		try {
			cutOff = orderInformationDao.getCutOff(timeslotID).getTime();
		} catch (NullPointerException e) {
			throw new NullCustomException(614, "Invalid timeslotID in TIMESLOT");
		}
		Long now = new Date().getTime() + TIME_GAP;		// VM 서버의 시간이 우리나라 시간과 9시간 차이 나므로 9시간을 더한 후 비교 
		
		if (cutOff < now) { // now 가 cutOff 이후 이면 에러
			throw new RangeNotSatisfyException(610, "Fail : CutOff has been passed");
		}
	}

	
	/**
	 * @author 신광원
	 * @description 권한체크 - 관리자는 무조건 통과, 사용자일 경우 로그인한 사용자와 주문자가 일치할 경우만 통과
	 */
	private void validateAuthority(int reservationID, String session) throws AuthException, NullCustomException {
		int privilege = sessionManager.getPrivilege(session);
		String loginUserID = sessionManager.getUserId(session);
		String orderUserID = getUserId(reservationID);
		if (ADMIN_PRIVILEGE != privilege && !loginUserID.equals(orderUserID)) {
			throw new AuthException(613, "Fail : Unauthorized");
		}
	}
}
