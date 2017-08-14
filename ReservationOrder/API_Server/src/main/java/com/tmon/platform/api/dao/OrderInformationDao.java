package com.tmon.platform.api.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tmon.platform.api.dto.OrderInformationDto;
import com.tmon.platform.api.dto.OrderProductDto;


public interface OrderInformationDao {

	public List<OrderInformationDto> selectByDate(Map<String, Object> betweenDate);

	public List<OrderInformationDto> selectByDateAndUser(Map<String, Object> paramValues);
	
	public int addOrder(Map<String, String> orderDto);
	public void addOrderProduct(int reservationID, List<Map<String, String>> productList);
	public void cancelOrder(int reservationID);
	public void incCountTimeSlot(int timeslotID);
	public void decCountTimeSlot(int timeslotID);
	
	
	public int getTimeSlotCount(int timeslotID);
	
	public void incSellQuantity(int productID, int quantity);
	public void decSellQuantity(int productID, int quantity);
	public int getStockQuantity(int productID);
	public List<OrderProductDto> getOrderProductList(int reservationID);
	public Map<String, Integer> getTimeSlotAndStatus (int reservationID);
	public String getUserId(int reservationID);
	public Date getCutOff(int timeslotID);
	
	
}
