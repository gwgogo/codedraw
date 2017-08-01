package com.tmon.platform.api.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import com.tmon.platform.api.dto.OrderInformationDto;
import com.tmon.platform.api.dto.OrderProductDto;
import com.tmon.platform.api.exception.AuthException;
import com.tmon.platform.api.exception.DateFormatException;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.exception.RangeNotSatisfyException;
import com.tmon.platform.api.exception.SQLCustomException;


public interface OrderInformationService {

	public List<OrderInformationDto> selectByDate(String searchInitDate, String searchFinishDate)
			throws DateFormatException, SQLCustomException;
	public List<OrderInformationDto> selectByDateAndUser(String searchInitDate, String searchFinishDate,
			String user_id) throws DateFormatException, SQLCustomException;
	public JSONObject addOrder(Map<String, String> orderDto, List<Map<String, String>> productList, boolean fromBasket)
			throws RangeNotSatisfyException, SQLCustomException, NullCustomException, ParseException;
	public void addOrderProduct(int reservationID, List<Map<String, String>> productList) throws SQLCustomException, NullCustomException, RangeNotSatisfyException;
	public void incCountTimeSlot(int timeslotID) throws NullCustomException, RangeNotSatisfyException;
	public JSONObject cancelOrder(int reservationID, String session)throws SQLCustomException, NullCustomException, RangeNotSatisfyException, AuthException;
	public void decCountTimeSlot(int timeslotID) throws NullCustomException, RangeNotSatisfyException;
	
	
	
	/* 밑에 메소드들은 테스트 때문에 잠시 인터페이스에 추가 - 추후 삭제하고 impl에서 private로 변경*/
	public int getTimeSlotCount(int timeslotID) throws NullCustomException ;
	public int getStockQuantity(int productID) throws NullCustomException ;
	public void incSellQuantity(List<Map<String, String>> productList) throws NullCustomException, RangeNotSatisfyException;
	public void decSellQuantity(int reservationID) throws NullCustomException;
	public List<OrderProductDto> getOrderProductList(int reservationID) throws NullCustomException;
}
