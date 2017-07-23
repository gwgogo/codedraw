package com.tmon.platform.api.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tmon.platform.api.dao.ReservationDao;
import com.tmon.platform.api.dto.OrderProductDto;
import com.tmon.platform.api.dto.ReservationDto;
import com.tmon.platform.api.dto.TimeSlotDto;

@Repository
public class ReservationDaoImpl implements ReservationDao {

	private static final Logger logger = LoggerFactory.getLogger(ReservationDaoImpl.class);
	
	@Autowired
	private SqlSession sqlSession;
	
	public int addReservation(ReservationDto reservationDto) {
		sqlSession.insert("ReservationMapper.addReservation", reservationDto);
		return reservationDto.getReservation_id();
	}
	
	
	public void addOrderProduct(OrderProductDto orderProductDto) {
		sqlSession.insert("ReservationMapper.addOrderProduct", orderProductDto);
	}

	public void incCountTimeSlot(int timeslot_id) {
		sqlSession.update("ReservationMapper.incCountTimeSlot", timeslot_id);
	}
	
	public void decCountTimeSlot(int timeslot_id) {
		sqlSession.update("ReservationMapper.decCountTimeSlot", timeslot_id);
	}

	public void updateStatusReservation(int reservation_id, int status_id) {
		Map<String, Integer> map = new HashMap();
		map.put("reservation_id", reservation_id);
		map.put("status_id", status_id);
		
		sqlSession.update("ReservationMapper.updateStatusReservation", map);
	}
	public int getTimeSlotId(int reservation_id) {
		return sqlSession.selectOne("ReservationMapper.getTimeSlotId", reservation_id);
	}
}
