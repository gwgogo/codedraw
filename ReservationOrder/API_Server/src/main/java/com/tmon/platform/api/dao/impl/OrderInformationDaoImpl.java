package com.tmon.platform.api.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tmon.platform.api.dao.OrderInformationDao;
import com.tmon.platform.api.dto.OrderInformationDto;
import com.tmon.platform.api.dto.OrderProductDto;


@Repository
public class OrderInformationDaoImpl implements OrderInformationDao {

	@Autowired
	SqlSession sqlSession;

	@Override
	public List<OrderInformationDto> selectByDate(Map<String, Object> parameters) {
		return sqlSession.selectList("OrderInformationMapper.selectByDate", parameters);
	}

	@Override
	public List<OrderInformationDto> selectByDateAndUser(Map<String, Object> parameters) {
		return sqlSession.selectList("OrderInformationMapper.selectByDateAndUser", parameters);
	}
	
	public int addOrder(Map<String, String> orderDto) {
		sqlSession.insert("OrderInformationMapper.addOrder", orderDto);
		return Integer.parseInt(orderDto.get("reservationID"));
	}
	
	
	public void addOrderProduct(int reservationID, List<Map<String, String>> productList) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("reservationID", reservationID);
		map.put("productList",productList);
		
		sqlSession.insert("OrderInformationMapper.addOrderProduct", map);
	}
	

	public void incCountTimeSlot(int timeslotID) {
		sqlSession.update("OrderInformationMapper.incCountTimeSlot", timeslotID);
	}
	
	public void decCountTimeSlot(int timeslotID) {
		sqlSession.update("OrderInformationMapper.decCountTimeSlot", timeslotID);
	}

	public void cancelOrder(int reservationID) {
		sqlSession.update("OrderInformationMapper.cancelOrder", reservationID);
	}
	
	public int getTimeSlotCount(int timeslotID) {
		return sqlSession.selectOne("OrderInformationMapper.getTimeSlotCount", timeslotID);
	}
	
	public void incSellQuantity(int productID, int quantity) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("productID", String.valueOf(productID));
		map.put("quantity", String.valueOf(quantity));
		sqlSession.update("OrderInformationMapper.incSellQuantity", map);
	}
	
	public void decSellQuantity(int productID, int quantity) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("productID", String.valueOf(productID));
		map.put("quantity", String.valueOf(quantity));
		sqlSession.update("OrderInformationMapper.decSellQuantity", map);
	}
	
	public int getStockQuantity(int productID) {
		return sqlSession.selectOne("OrderInformationMapper.getStockQuantity", productID);
	}
	
	public List<OrderProductDto> getOrderProductList(int reservationID){
		return sqlSession.selectList("OrderInformationMapper.getOrderProductList", reservationID);
	}
	
	public Map<String, Integer>getTimeSlotAndStatus (int reservationID){
		return sqlSession.selectOne("OrderInformationMapper.getTimeSlotAndStatus", reservationID);
	}

	public String getUserId(int reservationID) {
		return sqlSession.selectOne("OrderInformationMapper.getUserId", reservationID);
	}

	public Date getCutOff(int timeslotID) {
		return sqlSession.selectOne("OrderInformationMapper.getCutOff", timeslotID);
	}
	
}
