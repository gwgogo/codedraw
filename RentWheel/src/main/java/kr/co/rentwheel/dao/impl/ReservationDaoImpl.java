package kr.co.rentwheel.dao.impl;

import org.springframework.stereotype.Repository;

import kr.co.rentwheel.dao.ReservationDao;
import kr.co.rentwheel.dto.ReservationDto;

@Repository
public class ReservationDaoImpl implements ReservationDao{
	public boolean reservations(ReservationDto reservationDto){
		
		return true;
	}
}
