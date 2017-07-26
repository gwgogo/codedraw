package com.tmon.platform.api.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmon.platform.api.dao.OrderInformationDao;
import com.tmon.platform.api.dto.OrderInformationDto;
import com.tmon.platform.api.dto.OrderProductDto;
import com.tmon.platform.api.exception.DateFormatException;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.exception.RangeNotSatisfyException;
import com.tmon.platform.api.exception.SQLCustomException;
import com.tmon.platform.api.service.BasketService;
import com.tmon.platform.api.service.OrderInformationService;


@Service
public class OrderInformationServiceImpl implements OrderInformationService {

	private static final Logger logger = LoggerFactory.getLogger(OrderInformationServiceImpl.class);
	
	private static final int ORDER_COMPLETE = 0;
	private static final int MAX_TIMESLOT_COUNT = 5;

	@Autowired
	private OrderInformationDao orderInformationDao;

	@Autowired
	private SimpleDateFormat dateFormat;

	@Autowired 
	private BasketService basketService;
	
	@Override
	public List<OrderInformationDto> selectByDate(String search_init_date, String search_finish_date)
			throws DateFormatException, SQLCustomException {

		// WHERE절 변수 입력을 위해 parameterType을 Map구조로 한다.
		Map<String, Object> betweenDate = new HashMap<String, Object>();

		try {
			betweenDate.put("search_init_date", dateFormat.parse(search_init_date));
			betweenDate.put("search_finish_date", dateFormat.parse(search_finish_date));
		} catch (ParseException e) {
			logger.info("Erro at OrderInformation selectByDate");
			throw new DateFormatException(616, "Incorrect input Date data");
		}

		// 검색 시작 날짜가 검색 끝 날짜 보다 작아야 한다.
		long initDate = ((Date) betweenDate.get("search_init_date")).getTime();
		long finishDate = ((Date) betweenDate.get("search_finish_date")).getTime();
		if (initDate > finishDate) {
			logger.info("search_start_time: " + betweenDate.get("search_init_date"));
			logger.info("search_end_time: " + betweenDate.get("search_finish_date"));
			throw new DateFormatException(616, "search_init_date must be smaller than search_finish_date");
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
	public List<OrderInformationDto> selectByDateAndUser(String search_init_date, String search_finish_date,
			String user_id) throws DateFormatException, SQLCustomException {
		// WHERE절 변수 입력을 위해 parameterType을 Map구조로 한다.
		Map<String, Object> paramValues = new HashMap<String, Object>();

		try {
			paramValues.put("search_init_date", dateFormat.parse(search_init_date));
			paramValues.put("search_finish_date", dateFormat.parse(search_finish_date));
			paramValues.put("user_id", user_id);

		} catch (ParseException e) {
			logger.info("Erro at OrderInformation selectByDate");
			throw new DateFormatException(616, "Incorrect input Date data");
		}

		// 검색 시작 날짜가 검색 끝 날짜 보다 작아야 한다.
		long initDate = ((Date) paramValues.get("search_init_date")).getTime();
		long finishDate = ((Date) paramValues.get("search_finish_date")).getTime();
		if (initDate > finishDate) {
			logger.info("search_start_time: " + paramValues.get("search_init_date"));
			logger.info("search_end_time: " + paramValues.get("search_finish_date"));
			throw new DateFormatException(616, "search_init_date must be smaller than search_finish_date");
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
	
	
	/* 장바구니에 담긴 상품 전체 주문 */
	public JSONObject addOrder(Map<String, String> orderDto, List<Map<String,String>> productList)
			throws RangeNotSatisfyException, SQLCustomException, NullCustomException {
		
		int timeslot_id = Integer.parseInt(orderDto.get("timeslot_id"));
		
		int count = getTimeSlotCount(timeslot_id); 
		int reservation_id;
		
		if (count == MAX_TIMESLOT_COUNT) {
			throw new RangeNotSatisfyException(610, "TimeSlot Count Full");
		}
		try {
			reservation_id = orderInformationDao.addOrder(orderDto); 					// INSERT in RESERVATION_ORDER
		} catch (Exception e) {
			throw new SQLCustomException(611, "Fail : Insert Order SQL Error");
		}
		
		addOrderProduct(reservation_id, productList); 									// INSERT in ORDER_INFORMATION
		incCountTimeSlot(timeslot_id); 													// Increment TIMESLOT count
		if (count == MAX_TIMESLOT_COUNT - 1) {
			setTimeSlotCutOff(timeslot_id); 											// UPDATE TIMESLOT CutOff
		}
		
		//if (flag == 0)
		basketService.cleanBasket(orderDto.get("user_id"));								// Clean BASKET 					
		
		JSONObject obj = new JSONObject();
		obj.put("msg", "Success Add Order");
		return obj;
	}
	
	
	/* 즉시 구매 - 하나의 상품 바로 주문 */
	public JSONObject addInstantOrder(Map<String, String> orderDto, int product_id, int quantity) 
			throws RangeNotSatisfyException, SQLCustomException, NullCustomException {
		
		
		int timeslot_id = Integer.parseInt(orderDto.get("timeslot_id"));
		int count = getTimeSlotCount(timeslot_id); 
		int reservation_id;
		
		if (count == MAX_TIMESLOT_COUNT) {
			throw new RangeNotSatisfyException(610, "TimeSlot Count Full");
		}
		try {
			reservation_id = orderInformationDao.addInstantOrder(orderDto); 	// INSERT in RESERVATION_ORDER
		} catch (Exception e) {
			throw new SQLCustomException(611, "Fail : Insert InstantOrder SQL Error");
		}
		
		List<Map<String, String>> productList = new ArrayList();
		Map<String,String> map = new HashMap();
		map.put("product_id", String.valueOf(product_id));
		map.put("quantity", String.valueOf(quantity));
		productList.add(map);
		
		addOrderProduct(reservation_id, productList); 									// INSERT in ORDER_INFORMATION
		incCountTimeSlot(timeslot_id); 													// Increment TIMESLOT count
		basketService.cleanBasket(orderDto.get("user_id")); 							// Clean BASKET
		if (count == MAX_TIMESLOT_COUNT - 1) {
			setTimeSlotCutOff(timeslot_id); 											// UPDATE TIMESLOT CutOff
		}
		
		JSONObject obj = new JSONObject();
		obj.put("msg", "Success Add InstantOrder");
		return obj;
	}

	
	/* 주문에 따른 상품내역 ORDER_INFORMATION 테이블에 적재 */
	public void addOrderProduct(int reservation_id, List<Map<String,String>> productList) throws SQLCustomException, NullCustomException, RangeNotSatisfyException {

		for (int idx = 0; idx < productList.size(); idx++) {
			Map<String, String> product = productList.get(idx);
			product.put("reservation_id", String.valueOf(reservation_id));
			try {
				orderInformationDao.addOrderProduct(product);						// INSERT in ORDER_INFORMATION
			}catch(Exception e) {
				throw new SQLCustomException(612, "Fail : Insert OrderProduct SQL Error");
			}
			
			int product_id = Integer.parseInt(product.get("product_id"));
			int quantity = Integer.parseInt(product.get("quantity"));
			incSellQuantity(product_id, quantity);									// Increment sell_quantity in PRODUCT
		}
	}

	
	/* 주문 취소 */
	public JSONObject cancelOrder(int reservation_id) throws SQLCustomException, NullCustomException, RangeNotSatisfyException { 
		Map<String, Integer> timeSlotAndStatus = getTimeSlotAndStatus(reservation_id);	// reservation_id에 해당하는 주문이 없을 경우 예외 발생
		int status_id = timeSlotAndStatus.get("status_id").intValue();
		int timeslot_id = timeSlotAndStatus.get("timeslot_id").intValue();

		int count = getTimeSlotCount(timeslot_id);
		
		if(status_id != ORDER_COMPLETE) {	// 주문 완료 status가 아닌데 주문 취소 행위
			throw new RangeNotSatisfyException(615, "Can't cancel : OrderStatus is not ORDER_COMPLETE");
		} else if(count <= 0) {				// 주문이 없는 상태에서 주문 취소 행위
			throw new RangeNotSatisfyException(615, "Can't cancel : Timeslot's count less than zero");
		} 
		
		orderInformationDao.cancelOrder(reservation_id);
		if(count == MAX_TIMESLOT_COUNT) { 			// Timeslot Full Count -> cutoff 초기화
			resetTimeSlotCutOff(timeslot_id);
		}
		decCountTimeSlot(timeslot_id);			 	// timeslot count 감소
		
		
		/* 주문 취소시 상품 판매 수량 감소 */
		List<OrderProductDto> orderProductList = getOrderProductList(reservation_id);
		for(int idx = 0; idx < orderProductList.size(); idx++) {
			int product_id = orderProductList.get(idx).getProduct_id();
			int quantity = orderProductList.get(idx).getQuantity();
			decSellQuantity(product_id, quantity);							
		}
		
		JSONObject obj = new JSONObject();
		obj.put("msg", "Success Update Reservation");
		return obj;
	}

	/* inc , dec는 서비스 인터페이스에서 트랜잭션AOP 처리를 위해 public */
	/* 주문 완료시 해당하는 타임슬롯 카운트 증가 */
	private void incCountTimeSlot(int timeslot_id) throws NullCustomException, RangeNotSatisfyException {
		int count = getTimeSlotCount(timeslot_id);
		if(count >= 5) {
			throw new RangeNotSatisfyException(616, "Can't increse TimeslotCount : Timeslot Full Count");
		}
		orderInformationDao.incCountTimeSlot(timeslot_id);
	}

	/* 주문 취소시 해당하는 타임슬롯 카운트 감소 */
	public void decCountTimeSlot(int timeslot_id) throws NullCustomException, RangeNotSatisfyException {
		int count = getTimeSlotCount(timeslot_id);
		if(count <= 0) {
			throw new RangeNotSatisfyException(616, "Can't decrese TimeslotCount : Now, Timeslot Count is Zero");
		}
		orderInformationDao.decCountTimeSlot(timeslot_id);
	}

	/* 타임슬롯에 해당하는 타임슬롯count 가져오기*/
	private int getTimeSlotCount(int timeslot_id) throws NullCustomException {
		int count;
		try {
			count = orderInformationDao.getTimeSlotCount(timeslot_id);
		}catch(NullPointerException e) {
			throw new NullCustomException(614, "Invalid timeslot_id in TIMESLOT");
		}
		return count;
	}

	/* 타임슬롯 count가 5가 되는 시점에 cutoff 설정 */
	private void setTimeSlotCutOff(int timeslot_id) {
		orderInformationDao.setTimeSlotCutOff(timeslot_id);
	}

	/* 타임슬롯 count가 5인 상태에서 주문취소가 들어올 경우 cutoff 초기화 */
	private void resetTimeSlotCutOff(int timeslot_id) {
		orderInformationDao.resetTimeSlotCutOff(timeslot_id);
	}
	
	/* 주문 완료시 상품 구매 수량 만큼 sell_quantity 증가 */
	private void incSellQuantity(int product_id, int quantity) throws NullCustomException, RangeNotSatisfyException {
		int stockQuantity = getStockQuantity(product_id);
		if(stockQuantity < quantity) {
			throw new RangeNotSatisfyException(615, "incSellQuantity : stockQuantity less than orderQuantity");
		}
		orderInformationDao.incSellQuantity(product_id, quantity);
	}
	
	/* 주문 취소시 상품 구매 수량 만큼 sell_quantity 감소 */
	private void decSellQuantity(int product_id, int quantity){
		orderInformationDao.decSellQuantity(product_id, quantity);
	}
	
	private int getStockQuantity(int product_id) throws NullCustomException {
		int stockQuantity;
		try {
			stockQuantity = orderInformationDao.getStockQuantity(product_id);
		}catch(NullPointerException e) {
			throw new NullCustomException(614, "getStockQuantity : Invalid Product ID");
		}
		return stockQuantity;
	
	}
	
	/* 주문에 해당하는 상품 내역 가져오기 */
	private List<OrderProductDto> getOrderProductList(int reservation_id) throws NullCustomException{ 
		List<OrderProductDto> list = orderInformationDao.getOrderProductList(reservation_id);
		if(list.isEmpty()) {
			throw new NullCustomException(614, "Invalid reservation_id in ORDER_INFORMATION");
		}
		return list;
	}
	
	/* 주문에 해당하는 타임슬롯ID와 현재 주문상태 가져오기 */
	private Map<String,Integer>getTimeSlotAndStatus(int reservation_id) throws NullCustomException{ 
		Map<String, Integer> map = orderInformationDao.getTimeSlotAndStatus(reservation_id);
		if(map.isEmpty()) {
			throw new NullCustomException(614, "Invalid reservation_id in RESERVATION_ORDER");
		}
		return map; 
	}

}
