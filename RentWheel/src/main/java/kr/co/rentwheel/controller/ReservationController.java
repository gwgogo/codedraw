package kr.co.rentwheel.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.rentwheel.domain.CustomException;
import kr.co.rentwheel.domain.ResponseItem;
import kr.co.rentwheel.dto.ReservationDto;
import kr.co.rentwheel.dto.ReservationTimeDto;
import kr.co.rentwheel.service.ReservationService;

/*
 * rs_flag
 * 0 : ����Ϸ�
 * 1 : �̿�Ϸ�
 * 2 : �������
 */


@RestController
@RequestMapping(value="/reservation")
public class ReservationController {
	
	
	
	@Autowired
	private ReservationService reservationService;
	
	/* ���� ��� */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseItem addReservation(HttpServletRequest req) throws CustomException{
		ReservationDto reservationDto = new ReservationDto();
		reservationDto.setRs_user_id(req.getParameter("rs_user_id"));
		reservationDto.setRs_mobility_id(Integer.parseInt(req.getParameter("rs_mobility_id")));
		reservationDto.setRs_price(Integer.parseInt(req.getParameter("rs_price")));
		reservationDto.setRs_start_time(req.getParameter("rs_start_time"));
		reservationDto.setRs_end_time(req.getParameter("rs_end_time"));
		
		return reservationService.addReservation(reservationDto);
	}
	
	/* ���� ��ü ��ȸ  */
	@RequestMapping(method = RequestMethod.GET)
	public List<ReservationDto> getReservation() {
		return reservationService.getReservation();
	}
	
	/* �����ȣ�� ���� ���� ��ȸ */
	@RequestMapping(value="/{rs_id}", method = RequestMethod.GET)
	public ReservationDto getReservationByReservationID(@PathVariable("rs_id") int rs_id) {
		return reservationService.getReservationByReservationID(rs_id);
	}
	
	/* ����ڿ� ���� ���� ��ȸ */
	@RequestMapping(value="/user", method = RequestMethod.GET)
	public List<ReservationDto> getReservationByUserID(@RequestParam("rs_user_id") String rs_user_id) {
		return reservationService.getReservationByUserID(rs_user_id);
	}
	
	/* �����ȣ�� ���� ���� ���� */
	@RequestMapping(value="/{rs_id}", method = RequestMethod.DELETE)
	public ResponseItem deleteReservation(@PathVariable("rs_id") int rs_id) {
		return reservationService.deleteReservation(rs_id);
	}
	
	/* ���� ��� */
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseItem cancelReservation(@RequestParam("rs_id") int rs_id, @RequestParam("rs_user_id") String rs_user_id) throws CustomException {
		return reservationService.cancelReservation(rs_id, rs_user_id);
	}
	
	/* �����Ƽ�� ����Ǿ��� �ð� �������� - ����Ǿ��ִ� �ð��� ���������� ��Ÿ���� ����*/
	@RequestMapping(value="/time/{rs_mobility_id}", method=RequestMethod.GET)
	public List<ReservationTimeDto> getMobilityReservationTime(@PathVariable("rs_mobility_id")int rs_mobility_id) {
		
		return reservationService.getMobilityReservationTime(rs_mobility_id);
	}
	
}
