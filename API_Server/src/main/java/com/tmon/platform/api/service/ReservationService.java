package com.tmon.platform.api.service;

import java.util.List;

import org.json.simple.JSONObject;

import com.tmon.platform.api.dto.OrderProductDto;
import com.tmon.platform.api.dto.ReservationDto;
import com.tmon.platform.api.dto.TimeSlotDto;
import com.tmon.platform.api.exception.CustomException;

public interface ReservationService {
	public int addReservation(ReservationDto reservationDto) throws CustomException;
	public void addOrderProduct(OrderProductDto OrderProductDto) throws CustomException;
	public List<TimeSlotDto> validTimeSlot() throws CustomException;
	public JSONObject updateStatusReservation(int reservation_id, int status_id) throws CustomException;
}
