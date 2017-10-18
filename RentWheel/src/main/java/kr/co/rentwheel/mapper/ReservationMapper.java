package kr.co.rentwheel.mapper;

import java.util.List;
import java.util.Map;

import kr.co.rentwheel.dto.ReservationDto;
import kr.co.rentwheel.dto.ReservationTimeDto;

public interface ReservationMapper {
	public boolean addReservation(ReservationDto reservationDto);
	public ReservationDto getReservationByReservationID(int rs_id);
	public List<ReservationDto> getReservation();
	public List<ReservationDto> getReservationByUserID(String rs_user_id);
	public int deleteReservation(int rs_id);
	public int updateReservationState(Map<String,Integer> map);
	public List<Integer> getEndReservation();
	public List<ReservationDto> getReservationByAdmin();
	public List<ReservationDto> getReservationTime(Map<String,String> map);
	public int getReservationPrice(Map<String,String> map);
	public int cancelReservation(Map<String,String> map);
	public List<ReservationTimeDto> getMobilityReservationTime(int rs_mobility_id);
}
