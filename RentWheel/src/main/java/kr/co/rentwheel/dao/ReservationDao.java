package kr.co.rentwheel.dao;

import kr.co.rentwheel.dto.ReservationDto;

public interface ReservationDao {
	public boolean reservations(ReservationDto reservationDto);
}
