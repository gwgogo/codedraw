package kr.co.rentwheel.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.rentwheel.domain.ResponseItem;
import kr.co.rentwheel.service.ReservationService;

@Controller
public class ReservationController {
	
	@Autowired
	private ReservationService reservationService;
	
	@RequestMapping(value="/reservations", method = RequestMethod.GET)
	@ResponseBody
	public ResponseItem reservations(HttpServletRequest req){
		return null;
	}
}
