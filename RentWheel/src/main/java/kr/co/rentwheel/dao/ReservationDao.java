package kr.co.rentwheel.dao;

import java.util.List;

import kr.co.rentwheel.dto.ReservationDto;
import kr.co.rentwheel.dto.ReservationTimeDto;

public interface ReservationDao {
	public void addReservation(ReservationDto reservationDto);
	public ReservationDto getReservationByReservationID(int rs_id);
	public List<ReservationDto> getReservation();
	public List<ReservationDto> getReservationByUserID(String rs_user_id);
	public int deleteReservation(int rs_id);
	public int updateReservationState(int rs_id, int rs_flag);
	public List<Integer> getEndReservation();
	public List<ReservationDto> getReservationByAdmin();
	public List<ReservationDto> getReservationTime(ReservationDto dto);
	public int getReservationPrice(int rs_id, String rs_user_id);
	public int cancelReservation(int rs_id, String rs_user_id);
	public List<ReservationTimeDto> getMobilityReservationTime(int rs_mobility_id);
}
