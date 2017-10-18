package kr.co.rentwheel.service;

import java.util.List;

import kr.co.rentwheel.domain.CustomException;
import kr.co.rentwheel.domain.ResponseItem;
import kr.co.rentwheel.dto.ReservationDto;
import kr.co.rentwheel.dto.ReservationTimeDto;

public interface ReservationService {
	public ResponseItem addReservation(ReservationDto reservationDto) throws CustomException;
	public ReservationDto getReservationByReservationID(int rs_id);
	public List<ReservationDto> getReservation();
	public List<ReservationDto> getReservationByUserID(String rs_user_id);
	public ResponseItem deleteReservation(int rs_id);
	public ResponseItem updateReservationState(int rs_id, int rs_flag);
	public List<Integer> getEndReservation();
	public List<ReservationDto> getReservationByAdmin();
	public ResponseItem cancelReservation(int rs_id, String rs_user_id) throws CustomException;
	public List<ReservationTimeDto> getMobilityReservationTime(int rs_mobility_id);
}
