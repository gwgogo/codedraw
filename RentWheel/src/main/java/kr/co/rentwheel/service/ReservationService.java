package kr.co.rentwheel.service;

import kr.co.rentwheel.domain.ResponseItem;
import kr.co.rentwheel.dto.ReservationDto;

public interface ReservationService {
	public ResponseItem reservations(ReservationDto reservationDto);
}
