package kr.co.rentwheel.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.rentwheel.dao.ReservationDao;
import kr.co.rentwheel.domain.CustomException;
import kr.co.rentwheel.domain.ResponseItem;
import kr.co.rentwheel.dto.ReservationDto;
import kr.co.rentwheel.dto.ReservationTimeDto;
import kr.co.rentwheel.service.ReservationService;
import kr.co.rentwheel.service.UserService;

@Service
public class ReservationServiceImpl implements ReservationService{
	
	private static final int CANCEL_RESERVATION_STATE = 2;
	
	@Autowired
	private ReservationDao reservationDao;
	
	@Autowired
	private UserService userService;
	
	private static final Logger log = Logger.getLogger(ReservationServiceImpl.class);
	
	@Transactional(rollbackFor= CustomException.class) 
	public ResponseItem addReservation(ReservationDto reservationDto) throws CustomException{
		ResponseItem responseItem = new ResponseItem();
		
		String rs_user_id = reservationDto.getRs_user_id();
		int rs_price = reservationDto.getRs_price();
		int rs_mobility_id = reservationDto.getRs_mobility_id();
		if(!reservationTimeValidate(reservationDto)) {
			throw new CustomException("417","Fail : Duplicate ReservationTime");
		}

		try {
			reservationDao.addReservation(reservationDto);
		}catch(Exception e) {
			throw new CustomException("408", "Fail : reservation insert error");
		}
		userService.decreaseUserBalance(rs_user_id, rs_price);	// ����� �ܾ� ����
		userService.increaseAdminBalance(rs_price);				// ������ �ܾ� ����
		return responseItem;
	}

	public ReservationDto getReservationByReservationID(int rs_id){
		ReservationDto dto = reservationDao.getReservationByReservationID(rs_id);
		dto = timeConverter(dto);
		return dto;
	}
	
	public List<ReservationDto> getReservation(){
		List<ReservationDto> list = reservationDao.getReservation();
		for(int i = 0; i < list.size(); i++) {
			ReservationDto dto = list.get(i);
			dto = timeConverter(dto);
		}
		return list;
	}
	
	public List<ReservationDto> getReservationByUserID(String rs_user_id){
		List<ReservationDto> list = reservationDao.getReservationByUserID(rs_user_id);
		for(int i = 0; i < list.size(); i++) {
			ReservationDto dto = list.get(i);
			dto = timeConverter(dto);
		}
		return list;
	}
	
	public ResponseItem deleteReservation(int rs_id) {
		ResponseItem responseItem = new ResponseItem();
		ReservationDto dto = reservationDao.getReservationByReservationID(rs_id);
		if(dto.getRs_flag() == 1 || dto.getRs_flag() == 2) {
			responseItem.setMsg("Fail : Can't delete Reservation");
			responseItem.setResult("409");
		}
		else {
			int result = reservationDao.deleteReservation(rs_id);
			if(result == 0) {
				responseItem.setMsg("Fail : Invalid ReservationID");
				responseItem.setResult("410");
			}
		}
		return responseItem;
	}
	
	@Transactional(rollbackFor= CustomException.class) 
	public ResponseItem cancelReservation(int rs_id, String rs_user_id) throws CustomException {
		ResponseItem responseItem = reservationStateValidation(rs_id, CANCEL_RESERVATION_STATE);
		if(responseItem.getResult().equals("200")) {
			int rs_price = reservationDao.getReservationPrice(rs_id,rs_user_id);
			userService.decreaseAdminBalance(rs_price);				// ������ �ܾ� ����
			userService.increaseUserBalance(rs_user_id, rs_price);	// ����� �ܾ� ����
			
			if(reservationDao.cancelReservation(rs_id, rs_user_id) == 0) {	// ���� ��һ��·� ����
				throw new CustomException("419", "Fail : Reservation Cancel Query Error");
			}
		}
		return responseItem;
	}
	
	/* �����ٷ����� 30�и��� ����Ϸ� ���¿��� �̿�Ϸ� ���·� ������ �� */
	public ResponseItem updateReservationState(int rs_id, int rs_flag) {
		ResponseItem responseItem = reservationStateValidation(rs_id, rs_flag);
		if(responseItem.getResult().equals("200")) {
			reservationDao.updateReservationState(rs_id, rs_flag);
		}
		return responseItem;
	}
	
	public List<Integer> getEndReservation(){
		return reservationDao.getEndReservation();
	}
	
	public List<ReservationDto> getReservationByAdmin(){
		List<ReservationDto> list = reservationDao.getReservationByAdmin();
		for(int i = 0; i < list.size(); i++) {
			ReservationDto dto = list.get(i);
			dto = timeConverter(dto);
		}
		return list;
	}
	
	public List<ReservationTimeDto> getMobilityReservationTime(int rs_mobility_id) {
		List<ReservationTimeDto> list = reservationDao.getMobilityReservationTime(rs_mobility_id);
		for(int i = 0; i < list.size(); i++) {
			ReservationTimeDto dto = list.get(i);
			String start_time = dto.getRs_start_time();
			String end_time = dto.getRs_end_time();
			dto.setRs_start_time(start_time.substring(0,start_time.length() - 5));
			dto.setRs_end_time(end_time.substring(0,end_time.length() - 5));
		}
		
		/*String start_time = dto.getRs_start_time();
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date to = transFormat.parse(start_time);*/

		return list;
	}
	
	
	/* �����Ϸ��� �ð��� �ٸ� ����� ��ġ�°� �ִ��� ���� */
	private boolean reservationTimeValidate(ReservationDto dto){
		List<ReservationDto> list = reservationDao.getReservationTime(dto);
		if(list.isEmpty())
			return true;
		else
			return false;
	}
	
	
	/* �ð������� yyyy-MM-DD HH-mm ���� ��ȯ */
	private ReservationDto timeConverter(ReservationDto dto) {
		ReservationDto tmpDto = dto;
		String start_time = tmpDto.getRs_start_time();
		String end_time = tmpDto.getRs_end_time();
		
		/* �� ���� ����  */
		String tmp_time = start_time.substring(0, start_time.length() - 5);
		tmpDto.setRs_start_time(tmp_time);
		tmp_time = end_time.substring(0, end_time.length() - 5);
		tmpDto.setRs_end_time(tmp_time);
		return tmpDto;
	}
	
	
	/* ���� ���� ����� ���翹����¿��� �ٲٷ��� ������·� �ٲ� �� �ִ��� ���� */
	private ResponseItem reservationStateValidation(int rs_id, int rs_flag) {
		ResponseItem responseItem = new ResponseItem();
		
		ReservationDto dto = reservationDao.getReservationByReservationID(rs_id);
		int db_rs_flag = dto.getRs_flag();
		
		if(db_rs_flag == rs_flag) {
			responseItem.setMsg("Fail : Same reservation state");	// �����Ϸ��� ������·� �̹� ���õǾ��ִ� ���
			responseItem.setResult("411");
		}
		else if(rs_flag == 2 && db_rs_flag == 1) {	// �̿�Ϸ���¿��� ��������Ϸ��� ���
			responseItem.setMsg("Fail : Can't UseCompletionState to CancleState");
			responseItem.setResult("412");
		}
		else if(rs_flag == 1 && db_rs_flag == 2) {	// ������һ��¿��� �̿�Ϸ�� �����Ϸ��� ���
			responseItem.setMsg("Fail : Can't CancelState to UseCompletionState");
			responseItem.setResult("413");
		}
		else {
			int result = reservationDao.updateReservationState(rs_id, rs_flag); 
			if(result == 0) {
				responseItem.setMsg("Fail : Can't update ReservationState");
				responseItem.setResult("414");
			}
		}
		return responseItem;
	}
}
