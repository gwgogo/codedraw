package com.tmon.platform.api.dao.impl;

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
	public List<OrderInformationDto> selectByDate(Map<String, Object> betweenDate) {
		return sqlSession.selectList("OrderInformationMapper.selectByDate", betweenDate);
	}

	@Override
	public List<OrderInformationDto> selectByDateAndUser(Map<String, Object> paramValues) {
		return sqlSession.selectList("OrderInformationMapper.selectByDateAndUser", paramValues);
	}
	
	public int addOrder(Map<String, String> orderDto) {
		sqlSession.insert("OrderInformationMapper.addOrder", orderDto);
		return Integer.parseInt(orderDto.get("reservation_id"));
		
	}
	
	public int addInstantOrder(Map<String, String> orderDto) {
		sqlSession.insert("OrderInformationMapper.addOrder", orderDto);
		return Integer.parseInt(orderDto.get("reservation_id"));
	}
	
	public void addOrderProduct(Map<String, String> product) {
		sqlSession.insert("OrderInformationMapper.addOrderProduct", product);
	}
	

	public void incCountTimeSlot(int timeslot_id) {
		sqlSession.update("OrderInformationMapper.incCountTimeSlot", timeslot_id);
	}
	
	public void decCountTimeSlot(int timeslot_id) {
		sqlSession.update("OrderInformationMapper.decCountTimeSlot", timeslot_id);
	}

	public void cancelOrder(int reservation_id) {
		sqlSession.update("OrderInformationMapper.cancelOrder", reservation_id);
	}
	
	public int getTimeSlotId(int reservation_id) {
		return sqlSession.selectOne("OrderInformationMapper.getTimeSlotId", reservation_id);
	}
	
	public int getTimeSlotCount(int timeslot_id) {
		return sqlSession.selectOne("OrderInformationMapper.getTimeSlotCount", timeslot_id);
	}
	
	public void setTimeSlotCutOff(int timeslot_id) {
		sqlSession.update("OrderInformationMapper.setTimeSlotCutOff", timeslot_id);
	}
	
	public void resetTimeSlotCutOff(int timeslot_id) {
		sqlSession.update("OrderInformationMapper.resetTimeSlotCutOff", timeslot_id);
	}
	
	public void incSellQuantity(int product_id, int quantity) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("product_id", String.valueOf(product_id));
		map.put("quantity", String.valueOf(quantity));
		sqlSession.update("OrderInformationMapper.incQuantity", map);
	}
	
	public void decSellQuantity(int product_id, int quantity) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("product_id", String.valueOf(product_id));
		map.put("quantity", String.valueOf(quantity));
		sqlSession.update("OrderInformationMapper.decQuantity", map);
	}
	
	public int getStockQuantity(int product_id) {
		return sqlSession.selectOne("OrderInformationMapper.getStockQuantity", product_id);
	}
	
	public List<OrderProductDto> getOrderProductList(int reservation_id){
		return sqlSession.selectList("OrderInformationMapper.getOrderProductList", reservation_id);
	}

	public int getOrderStatus(int reservation_id) {
		return sqlSession.selectOne("OrderInformationMapper.getOrderStatus", reservation_id);
	}
	
	public Map<String, Integer>getTimeSlotAndStatus (int reservation_id){
		return sqlSession.selectOne("OrderInformationMapper.getTimeSlotAndStatus", reservation_id);
	}
}
