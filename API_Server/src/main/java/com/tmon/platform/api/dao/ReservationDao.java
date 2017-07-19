package com.tmon.platform.api.dao;

import java.util.List;

import com.tmon.platform.api.dto.OrderProductDto;
import com.tmon.platform.api.dto.ReservationDto;
import com.tmon.platform.api.dto.TimeSlotDto;

public interface ReservationDao {
	public int addReservation(ReservationDto reservationDto);
	public void addOrderProduct(OrderProductDto OrderProductDto);
	public List<TimeSlotDto> validTimeSlot();
	public int updateStatusReservation(int reservation_id, int status_id);
	public void incCountTimeSlot(int timeslot_id);
	public void decCountTimeSlot(int timeslot_id);
}
