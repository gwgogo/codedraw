package com.tmon.platform.api.dao;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import com.tmon.platform.api.dto.OrderInformationDto;
import com.tmon.platform.api.dto.OrderProductDto;


public interface OrderInformationDao {

	public List<OrderInformationDto> selectByDate(Map<String, Object> betweenDate);

	public List<OrderInformationDto> selectByDateAndUser(Map<String, Object> paramValues);
	
	public int addOrder(Map<String, String> orderDto);
	public int addInstantOrder(Map<String, String> orderDto);
	public void addOrderProduct(Map<String, String> product);
	public void cancelOrder(int reservation_id);
	public void incCountTimeSlot(int timeslot_id);
	public void decCountTimeSlot(int timeslot_id);
	
	public int getTimeSlotId(int reservation_id);
	public int getTimeSlotCount(int timeslot_id);
	public void setTimeSlotCutOff(int timeslot_id);
	public void resetTimeSlotCutOff(int timeslot_id);
	
	public void incSellQuantity(int product_id, int quantity);
	public void decSellQuantity(int product_id, int quantity);
	public int getStockQuantity(int product_id);
	public List<OrderProductDto> getOrderProductList(int reservation_id);
	public int getOrderStatus(int reservation_id);
	public Map<String, Integer> getTimeSlotAndStatus (int reservation_id);
	
}
