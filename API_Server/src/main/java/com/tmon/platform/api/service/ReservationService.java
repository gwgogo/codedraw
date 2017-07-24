package com.tmon.platform.api.service;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import com.tmon.platform.api.dto.OrderProductDto;
import com.tmon.platform.api.dto.ReservationDto;
import com.tmon.platform.api.dto.TimeSlotDto;
import com.tmon.platform.api.exception.CustomException;
import com.tmon.platform.api.exception.RangeNotSatisfyException;
import com.tmon.platform.api.exception.SQLCustomException;

public interface ReservationService {
	

	//@Transactional(rollbackFor=CustomException.class)
	public JSONObject addReservation(ReservationDto reservationDto, List<OrderProductDto> orderProductDtoList) throws RangeNotSatisfyException, SQLCustomException;
	//@Transactional(rollbackFor=CustomException.class)
	public void addOrderProduct(int reservation_id, List<OrderProductDto> orderProductDtoList) throws SQLCustomException;
	//@Transactional(rollbackFor=CustomException.class)
	public void incCountTimeSlot(int timeslot_id);
	
	
	
	//@Transactional(rollbackFor=CustomException.class)
	public JSONObject updateStatusReservation(int reservation_id, int status_id) throws SQLCustomException;
	//@Transactional(rollbackFor=CustomException.class)
	public void decCountTimeSlot(int timeslot_id);
	
	
	
	public int getTimeSlotId(int reservation_id);
	public int getTimeSlotCount(int timeslot_id) throws CustomException;
	public void setTimeSlotCutOff(int timeslot_id);
	public void resetTimeSlotCutOff(int timeslot_id);
}
