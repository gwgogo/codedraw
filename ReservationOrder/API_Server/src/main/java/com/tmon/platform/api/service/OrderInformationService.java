package com.tmon.platform.api.service;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import com.tmon.platform.api.dto.OrderInformationDto;
import com.tmon.platform.api.exception.AuthException;
import com.tmon.platform.api.exception.DateFormatException;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.exception.RangeNotSatisfyException;
import com.tmon.platform.api.exception.SQLCustomException;


public interface OrderInformationService {

	public List<OrderInformationDto> selectByDate(String searchInitDate, String searchFinishDate)
			throws DateFormatException, SQLCustomException;

	public List<OrderInformationDto> selectByDateAndUser(String searchInitDate, String searchFinishDate, String user_id)
			throws DateFormatException, SQLCustomException, NullCustomException;
	
	public int addOrder(Map<String, String> orderDto, List<Map<String, String>> productList, boolean fromBasket) throws RangeNotSatisfyException, SQLCustomException, NullCustomException;
	public void addOrderProduct(int reservationID, List<Map<String, String>> productList) throws SQLCustomException, NullCustomException, RangeNotSatisfyException;
	public JSONObject cancelOrder(int reservationID, String session)throws SQLCustomException, NullCustomException, RangeNotSatisfyException, AuthException;
	
	public void incCountTimeSlot(int timeslotID) throws NullCustomException, RangeNotSatisfyException;
	public void decCountTimeSlot(int timeslotID) throws NullCustomException, RangeNotSatisfyException;
	public void decSellQuantity(int reservationID) throws NullCustomException ;
	public void incSellQuantity(List<Map<String, String>> productList) throws NullCustomException, RangeNotSatisfyException ;
}
