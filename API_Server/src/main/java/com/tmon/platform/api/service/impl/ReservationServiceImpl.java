package com.tmon.platform.api.service.impl;

import java.util.List;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmon.platform.api.dao.BasketDao;
import com.tmon.platform.api.dao.ReservationDao;
import com.tmon.platform.api.dto.OrderProductDto;
import com.tmon.platform.api.dto.ReservationDto;
import com.tmon.platform.api.dto.TimeSlotDto;
import com.tmon.platform.api.exception.CustomException;
import com.tmon.platform.api.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {
private static final Logger logger = LoggerFactory.getLogger(ReservationServiceImpl.class);
	
	private static final int CANCLE_STATUS = 3;
	
	@Autowired
	private ReservationDao reservationDao;
	
	@Autowired
	private BasketDao basketDao;
	
	public int addReservation(ReservationDto reservationDto) throws CustomException{
		int reservation_id ;
		try {
			reservation_id = reservationDao.addReservation(reservationDto);		// 주문테이블에 적재하고 Auto Increment 된 reservation_id 값 리턴
			reservationDao.incCountTimeSlot(reservationDto.getTimeslot_id());	// 주문테이블에 적재 후에 timeslot count 증가
			basketDao.cleanBasket(reservationDto.getUser_id());					// 주문완료되면 장바구니 clean
		}catch(Exception e) {
			throw new CustomException(501, "Fail Insert Reservation");
		}
		return reservation_id;
	}
	
	public void addOrderProduct(OrderProductDto orderProductDto) throws CustomException {
		try {
			reservationDao.addOrderProduct(orderProductDto);
			
		}catch(Exception e) {
			throw new CustomException(501, "Fail Insert OrderProduct");
		}
	}
	
	public List<TimeSlotDto> validTimeSlot() throws CustomException{
		List<TimeSlotDto> list = reservationDao.validTimeSlot();
		if(list == null) {
			throw new CustomException(501, "Not Exist Valid TimeSlot");
		}
		return list;
	}
	
	public JSONObject updateStatusReservation(int reservation_id, int status_id) throws CustomException {
		try {
			int timeslot_id = reservationDao.updateStatusReservation(reservation_id, status_id);
			if(status_id == CANCLE_STATUS) {
				reservationDao.decCountTimeSlot(timeslot_id);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new CustomException(501, "Fail Update StatusReservation");
		}
		return null;
	
	}
}
