package com.tmon.platform.api.service.impl;

import java.util.List;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmon.platform.api.dao.ReservationDao;
import com.tmon.platform.api.dto.OrderProductDto;
import com.tmon.platform.api.dto.ReservationDto;
import com.tmon.platform.api.dto.TimeSlotDto;
import com.tmon.platform.api.exception.CustomException;
import com.tmon.platform.api.service.BasketService;
import com.tmon.platform.api.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {
private static final Logger logger = LoggerFactory.getLogger(ReservationServiceImpl.class);
	
	private static final int CANCEL_STATUS = 3;
	
	@Autowired
	private ReservationDao reservationDao;
	
	@Autowired
	private BasketService basketService;
	
	public JSONObject addReservation(ReservationDto reservationDto, List<OrderProductDto> orderProductDtoList) throws CustomException{
		int timeslot_id = reservationDto.getTimeslot_id();
		
		try {
			
			int count = getTimeSlotCount(timeslot_id);
			if(count == 4) {
				setTimeSlotCutOff(timeslot_id);										// UPDATE TIMESLOT CutOff  
			}
			else if(count >= 5) {
				throw new Exception("Fail TimeSlot Count Full");					
			}
			int reservation_id = reservationDao.addReservation(reservationDto);	 	//  INSERT in RESERVATION_ORDER 
			addOrderProduct(reservation_id, orderProductDtoList);					//  INSERT in ORDER_INFORMATION
			incCountTimeSlot(timeslot_id);											//  Increment TIMESLOT count
			basketService.cleanBasket(reservationDto.getUser_id());					//  Clean BASKET
			
		}catch(Exception e) {
			//throw new CustomException(501, e.getMessage());
			throw new CustomException(555, "Fail Add Reservation");
		}
		JSONObject obj = new JSONObject();
		obj.put("msg", "Success Add Reservation");
		return obj;
	}
	
	public void addOrderProduct(int reservation_id, List<OrderProductDto> orderProductDtoList) throws CustomException {
	
		try {
			for(int idx = 0; idx < orderProductDtoList.size(); idx++) {
				OrderProductDto orderProductDto = orderProductDtoList.get(idx);
				orderProductDto.setReservation_id(reservation_id);
				reservationDao.addOrderProduct(orderProductDto);
			}
		}catch(Exception e) {
			throw new CustomException(501, e.getMessage());
			//throw new CustomException(501, "Fail Insert OrderProduct");
		}
	}
	
	public JSONObject updateStatusReservation(int reservation_id, int status_id) throws CustomException {
		try {
			
			int timeslot_id = getTimeSlotId(reservation_id);
			int count = getTimeSlotCount(timeslot_id);
			if(count == 5) {
				resetTimeSlotCutOff(timeslot_id);
			}
			reservationDao.updateStatusReservation(reservation_id, status_id);
			if(status_id == CANCEL_STATUS) {
				decCountTimeSlot(timeslot_id);
			}
		}catch(Exception e){
			throw new CustomException(501, e.getMessage());
			//throw new CustomException(501, "Fail Update StatusReservation");
		}
		JSONObject obj = new JSONObject();
		obj.put("msg", "Success Update Reservation");
		return obj;
	
	}
	
	public void incCountTimeSlot(int timeslot_id) {
		reservationDao.incCountTimeSlot(timeslot_id);
	}
	
	public void decCountTimeSlot(int timeslot_id) {
		reservationDao.decCountTimeSlot(timeslot_id);
	}
	
	public int getTimeSlotId(int reservation_id) {
		return reservationDao.getTimeSlotId(reservation_id);
	}
	
	public int getTimeSlotCount(int timeslot_id) throws CustomException {
		throw new CustomException(555, " test error!!");
		//return reservationDao.getTimeSlotCount(timeslot_id);
	}
	
	public void setTimeSlotCutOff(int timeslot_id) {
		reservationDao.setTimeSlotCutOff(timeslot_id);
	}
	
	public void resetTimeSlotCutOff(int timeslot_id) {
		reservationDao.resetTimeSlotCutOff(timeslot_id);
	}
	
}
