package com.tmon.platform.api.service;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import com.tmon.platform.api.dto.OrderInformationDto;
import com.tmon.platform.api.exception.DateFormatException;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.exception.RangeNotSatisfyException;
import com.tmon.platform.api.exception.SQLCustomException;


public interface OrderInformationService {

	public List<OrderInformationDto> selectByDate(String search_init_date, String search_finish_date)
			throws DateFormatException, SQLCustomException;

	public List<OrderInformationDto> selectByDateAndUser(String search_init_date, String search_finish_date,
			String user_id) throws DateFormatException, SQLCustomException;

	
	// @Transactional(rollbackFor=CustomException.class)
	public JSONObject addOrder(Map<String, String> orderDto, List<Map<String, String>> productList)
			throws RangeNotSatisfyException, SQLCustomException, NullCustomException;
	
	public JSONObject addInstantOrder(Map<String, String> orderDto ,int product_id, int quantity) 
			throws RangeNotSatisfyException, SQLCustomException, NullCustomException;

	// @Transactional(rollbackFor=CustomException.class)
	public void addOrderProduct(int reservation_id, List<Map<String, String>> productList) throws SQLCustomException, NullCustomException, RangeNotSatisfyException;

	// @Transactional(rollbackFor=CustomException.class)
	//public void incCountTimeSlot(int timeslot_id) throws NullCustomException, RangeNotSatisfyException;

	// @Transactional(rollbackFor=CustomException.class)
	public JSONObject cancelOrder(int reservation_id)throws SQLCustomException, NullCustomException, RangeNotSatisfyException;

	// @Transactional(rollbackFor=CustomException.class)
	public void decCountTimeSlot(int timeslot_id) throws NullCustomException, RangeNotSatisfyException;
	
}
