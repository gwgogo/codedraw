package kr.co.rentwheel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.rentwheel.dao.ReservationDao;
import kr.co.rentwheel.domain.ResponseItem;
import kr.co.rentwheel.dto.ReservationDto;
import kr.co.rentwheel.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService{
	@Autowired
	private ReservationDao reservationDao;
	
	public ResponseItem reservations(ReservationDto reservationDto){
		
		return null;
	}

}
