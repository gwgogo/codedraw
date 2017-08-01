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

import com.ibm.icu.util.Calendar;
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
import com.tmon.platform.api.util.SessionManager;

@Service
public class OrderInformationServiceImpl implements OrderInformationService {

	private static final Logger logger = LoggerFactory.getLogger(OrderInformationServiceImpl.class);

	private static final int ORDER_COMPLETE = 0;
	private static final int MAX_TIMESLOT_COUNT = 5;
	private static final int ADMIN_PRIVILEGE = 1;
	private static final long TIME_GAP = 32400000;

	@Autowired
	private OrderInformationDao orderInformationDao;

	@Autowired
	private SimpleDateFormat dateFormat;

	@Autowired
	private SimpleDateFormat cutOffTimeFormat;

	@Autowired
	private BasketService basketService;

	@Autowired
	private SessionManager sessionManager;

	@Override
	public List<OrderInformationDto> selectByDate(String searchInitDate, String searchFinishDate)
			throws DateFormatException, SQLCustomException {

		// WHERE절 변수 입력을 위해 parameterType을 Map구조로 한다.
		Map<String, Object> betweenDate = new HashMap<String, Object>();

		try {
			betweenDate.put("searchInitDate", dateFormat.parse(searchInitDate));
			betweenDate.put("searchFinishDate", dateFormat.parse(searchFinishDate));
		} catch (ParseException e) {
			logger.info("Erro at OrderInformation selectByDate");
			throw new DateFormatException(616, "Incorrect input Date data");
		}

		// 검색 시작 날짜가 검색 끝 날짜 보다 작아야 한다.
		long initDate = ((Date) betweenDate.get("searchInitDate")).getTime();
		long finishDate = ((Date) betweenDate.get("searchFinishDate")).getTime();
		if (initDate > finishDate) {
			logger.info("searchInitDate: " + betweenDate.get("searchInitDate"));
			logger.info("searchFinishDate: " + betweenDate.get("searchFinishDate"));
			throw new DateFormatException(616, "searchInitDate must be smaller than searchFinishDate");
		}

		try {
			// Dao에서는 Map객체를 parameter로 받는다.
			return orderInformationDao.selectByDate(betweenDate);
		} catch (Exception e) {
			// OrderInformation Select 실패
			logger.error(e.toString());
			throw new SQLCustomException(615, "Fail Select ORDER_INFORMATION By Date Error");
		}

	}

	@Override
	public List<OrderInformationDto> selectByDateAndUser(String searchInitDate, String searchFinishDate, String userID)
			throws DateFormatException, SQLCustomException {
		// WHERE절 변수 입력을 위해 parameterType을 Map구조로 한다.
		Map<String, Object> paramValues = new HashMap<String, Object>();
		try {
			paramValues.put("searchInitDate", dateFormat.parse(searchInitDate));
			paramValues.put("searchFinishDate", dateFormat.parse(searchFinishDate));
			paramValues.put("userID", userID);

		} catch (ParseException e) {
			logger.info("Erro at OrderInformation selectByDate");
			throw new DateFormatException(616, "Incorrect input Date data");
		}

		// 검색 시작 날짜가 검색 끝 날짜 보다 작아야 한다.
		long initDate = ((Date) paramValues.get("searchInitDate")).getTime();
		long finishDate = ((Date) paramValues.get("searchFinishDate")).getTime();
		if (initDate > finishDate) {
			logger.info("searchInitDate: " + paramValues.get("searchInitDate"));
			logger.info("searchFinishDate: " + paramValues.get("searchFinishDate"));
			throw new DateFormatException(616, "searchInitDate must be smaller than searchFinishDate");
		}

		try {
			// Dao에서는 Map객체를 parameter로 받는다.
			return orderInformationDao.selectByDateAndUser(paramValues);
		} catch (Exception e) {
			// OrderInformation Select 실패
			logger.error(e.toString());
			throw new SQLCustomException(615, "Fail Select ORDER_INFORMATION By Date And User Error");
		}
	}

	/* 주문 현황 적재 */
	public JSONObject addOrder(Map<String, String> orderDto, List<Map<String, String>> productList, boolean fromBasket)
			throws RangeNotSatisfyException, SQLCustomException, NullCustomException, ParseException {
		int reservationID;
		int timeslotID = Integer.parseInt(orderDto.get("timeslotID"));

		validateCutOffTime(timeslotID);
		try {
			reservationID = orderInformationDao.addOrder(orderDto); 					// RESERVATION_ORDER 테이블에 주문 현황 적재
		} catch (Exception e) {
			throw new SQLCustomException(611, "Fail : Insert Order SQL Error");
		}
		addOrderProduct(reservationID, productList); 									// ORDER_INFORMATION 테이블에 주문상품 내역 적재
		incCountTimeSlot(timeslotID); 													// 해당 TIMESLOT의 count 증가
		if (fromBasket) {																// 장바구니 전체 주문일 경우에만 장바구니 초기화 
			basketService.cleanBasket(orderDto.get("userID"));							 					
		}

		JSONObject obj = new JSONObject();
		obj.put("msg", "Success Add Order");
		return obj;
	}

	/* 주문 내역에 있는 상품리스트 적재 */
	public void addOrderProduct(int reservationID, List<Map<String, String>> productList)
			throws SQLCustomException, NullCustomException, RangeNotSatisfyException {
		
		incSellQuantity(productList);											// 상품 판매량 증가
		for (int idx = 0; idx < productList.size(); idx++) {
			Map<String, String> product = productList.get(idx);
			product.put("reservationID", String.valueOf(reservationID));
			try {
				orderInformationDao.addOrderProduct(product);						// 주문 내역의 상품 리스트 적재
			}catch(Exception e) {
				throw new SQLCustomException(612, "Fail : Insert OrderProduct SQL Error");
			}

			int productID = Integer.parseInt(product.get("productID"));
			int quantity = Integer.parseInt(product.get("quantity"));
			//incSellQuantity(productID, quantity);									// 상품 판매량 증가
		}
	}

	/* 주문 취소 */
	public JSONObject cancelOrder(int reservationID, String session)
			throws SQLCustomException, NullCustomException, RangeNotSatisfyException, AuthException {

		int privilege = sessionManager.getPrivilege(session);
		String loginUserID = sessionManager.getUserId(session);
		String orderUserID = getUserId(reservationID);
		if(ADMIN_PRIVILEGE != privilege && !loginUserID.equals(orderUserID)) {
			throw new AuthException(613, "Can't Delete Reservation : Unauthorized");
		}

		Map<String, Integer> timeSlotAndStatus = getTimeSlotAndStatus(reservationID);
		int statusID = timeSlotAndStatus.get("statusID").intValue();
		int timeslotID = timeSlotAndStatus.get("timeslotID").intValue();
		
		if(ORDER_COMPLETE != statusID) {				// 주문 완료 status가 아닌데 주문 취소 행위
			throw new RangeNotSatisfyException(615, "Can't cancel : OrderStatus is not ORDER_COMPLETE");
		}
		
		validateCutOffTime(timeslotID);					// cutOff 체크
		orderInformationDao.cancelOrder(reservationID);	// 주문취소 상태로 변경
		decCountTimeSlot(timeslotID);					// 타임슬롯 count 감소
		decSellQuantity(reservationID);					// 주문 취소시 상품 판매 수량 원상복구 
		
		JSONObject obj = new JSONObject();
		obj.put("msg", "Success Update Reservation");
		return obj;
	}

	
	/* inc , dec는 서비스 인터페이스에서 트랜잭션AOP 처리를 위해 public */
	/* 주문 완료시 해당하는 타임슬롯 카운트 증가 */
	public void incCountTimeSlot(int timeslotID) throws NullCustomException, RangeNotSatisfyException {
		int count = getTimeSlotCount(timeslotID);
		if (MAX_TIMESLOT_COUNT <= count ) {
			throw new RangeNotSatisfyException(616, "Can't increse TimeslotCount : Timeslot Full Count");
		}
		orderInformationDao.incCountTimeSlot(timeslotID);
	}

	/* 주문 취소시 해당하는 타임슬롯 카운트 감소 */
	public void decCountTimeSlot(int timeslotID) throws NullCustomException, RangeNotSatisfyException {
		int count = getTimeSlotCount(timeslotID);
		if (count <= 0) {
			throw new RangeNotSatisfyException(616, "Can't decrese TimeslotCount : Now, Timeslot Count is Zero");
		}
		orderInformationDao.decCountTimeSlot(timeslotID);
	}

	/* 밑에 메소드들은 private 지만 테스트를 위해 잠시 public - 추후 private로 변경 */
	/* 타임슬롯에 해당하는 타임슬롯count 가져오기 */
	public int getTimeSlotCount(int timeslotID) throws NullCustomException {
		int count;
		try {
			count = orderInformationDao.getTimeSlotCount(timeslotID);
		} catch (NullPointerException e) {
			throw new NullCustomException(614, "Invalid timeslotID in TIMESLOT");
		}
		return count;
	}

	/* 주문 완료시 상품 구매 수량 만큼 sell_quantity 증가 */
	public void incSellQuantity(List<Map<String, String>> productList) throws NullCustomException, RangeNotSatisfyException {
		
		for(int idx = 0; idx < productList.size(); idx ++) {
			int productID = Integer.parseInt(productList.get(idx).get("productID"));
			int quantity = Integer.parseInt(productList.get(idx).get("quantity"));
			int stockQuantity = getStockQuantity(productID);
			if (stockQuantity < quantity) {
				throw new RangeNotSatisfyException(615, "incSellQuantity : stockQuantity less than orderQuantity");
			}
			orderInformationDao.incSellQuantity(productID, quantity);
		}
	}

	
	/* 주문 취소시 상품 구매 수량 만큼 sell_quantity 감소 */
	public void decSellQuantity(int reservationID) throws NullCustomException {
		List<OrderProductDto> orderProductList = getOrderProductList(reservationID);
		for (int idx = 0; idx < orderProductList.size(); idx++) {
			int productID = orderProductList.get(idx).getProductID();
			int quantity = orderProductList.get(idx).getQuantity();
			orderInformationDao.decSellQuantity(productID, quantity);
		}
		//orderInformationDao.decSellQuantity(orderProductList);
	}

	
	/* 상품의 재고량 가져오기 (max_quantity - sell_quantity) */
	public int getStockQuantity(int productID) throws NullCustomException {
		int stockQuantity;
		try {
			stockQuantity = orderInformationDao.getStockQuantity(productID);
		} catch (NullPointerException e) {
			throw new NullCustomException(614, "getStockQuantity : Invalid Product ID");
		}
		return stockQuantity;

	}

	
	/* 주문에 해당하는 상품 내역 가져오기 */
	public List<OrderProductDto> getOrderProductList(int reservationID) throws NullCustomException {
		List<OrderProductDto> list = orderInformationDao.getOrderProductList(reservationID);
		if (list.isEmpty()) {
			throw new NullCustomException(614, "Invalid reservationID in ORDER_INFORMATION");
		}
		return list;
	}

	
	/* 주문에 해당하는 타임슬롯ID와 현재 주문상태 가져오기 */
	private Map<String, Integer> getTimeSlotAndStatus(int reservationID) throws NullCustomException {
		Map<String, Integer> map = orderInformationDao.getTimeSlotAndStatus(reservationID);
		if (map == null) {
			throw new NullCustomException(614, "Invalid reservationID in RESERVATION_ORDER");
		}
		return map;
	}

	/* 주문에 해당하는 userID 가져오기 - 주문과 일치하지 않은 USER가 주문번호를 통해 주문을 취소하려는 경우를 막기 위해 사용 */
	private String getUserId(int reservationID) throws NullCustomException {
		String userID = orderInformationDao.getUserId(reservationID);
		if(userID == null) {
			throw new NullCustomException(614, "Invalid reservationID in RESERVATION_ORDER");
		}
		return userID;
	}

	/* 현재 시간이 CutOff 시간을 지나지 않았는지 확인 */
	private void validateCutOffTime(int timeslotID) throws RangeNotSatisfyException {
		
		Long cutOff = orderInformationDao.getCutOff(timeslotID).getTime();
		Long now = new Date().getTime() + TIME_GAP;
		
		if(cutOff < now) {	// now 가 cutOff 이후 이면
			throw new RangeNotSatisfyException(610, "Fail : CutOff has been passed");
		}
	}

}
