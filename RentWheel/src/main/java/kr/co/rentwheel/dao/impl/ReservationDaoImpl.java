package kr.co.rentwheel.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.rentwheel.dao.ReservationDao;
import kr.co.rentwheel.dto.ReservationDto;
import kr.co.rentwheel.dto.ReservationTimeDto;
import kr.co.rentwheel.mapper.ReservationMapper;

@Repository
public class ReservationDaoImpl implements ReservationDao{
	
	@Autowired
	private SqlSession sqlSession;
	
	static Logger log = Logger.getLogger(ReservationDaoImpl.class);
	
	
	public void addReservation(ReservationDto reservationDto){
		ReservationMapper reservationMapper = sqlSession.getMapper(ReservationMapper.class);
		reservationMapper.addReservation(reservationDto);
	}
	
	public ReservationDto getReservationByReservationID(int rs_id){
		ReservationMapper reservationMapper = sqlSession.getMapper(ReservationMapper.class);
		return reservationMapper.getReservationByReservationID(rs_id);
	}
	
	public List<ReservationDto> getReservation(){
		ReservationMapper reservationMapper = sqlSession.getMapper(ReservationMapper.class);
		return reservationMapper.getReservation();
	}
	
	public List<ReservationDto> getReservationByUserID(String rs_user_id){
		ReservationMapper reservationMapper = sqlSession.getMapper(ReservationMapper.class);
		return reservationMapper.getReservationByUserID(rs_user_id);
	}
	
	public int deleteReservation(int rs_id) {
		ReservationMapper reservationMapper = sqlSession.getMapper(ReservationMapper.class);
		return reservationMapper.deleteReservation(rs_id);
	}
	
	public int updateReservationState(int rs_id, int rs_flag) {
		ReservationMapper reservationMapper = sqlSession.getMapper(ReservationMapper.class);
		Map<String, Integer> map = new HashMap();
		map.put("rs_id", rs_id);
		map.put("rs_flag", rs_flag);
		return reservationMapper.updateReservationState(map);
	}
	
	public List<Integer> getEndReservation(){
		ReservationMapper reservationMapper = sqlSession.getMapper(ReservationMapper.class);
		return reservationMapper.getEndReservation();
	}
	
	public List<ReservationDto> getReservationByAdmin(){
		ReservationMapper reservationMapper = sqlSession.getMapper(ReservationMapper.class);
		return reservationMapper.getReservationByAdmin();
	}
	
	public List<ReservationDto> getReservationTime(ReservationDto dto){
		ReservationMapper reservationMapper = sqlSession.getMapper(ReservationMapper.class);
		Map<String, String> map = new HashMap();
		map.put("rs_mobility_id", String.valueOf(dto.getRs_mobility_id()));
		map.put("rs_start_time", dto.getRs_start_time());
		map.put("rs_end_time", dto.getRs_end_time());
		return reservationMapper.getReservationTime(map);
	}
	
	public int getReservationPrice(int rs_id, String rs_user_id) {
		ReservationMapper reservationMapper = sqlSession.getMapper(ReservationMapper.class);
		Map<String, String> map = new HashMap();
		map.put("rs_id", String.valueOf(rs_id));
		map.put("rs_user_id", rs_user_id);
		return reservationMapper.getReservationPrice(map);
	}
	
	public int cancelReservation(int rs_id, String rs_user_id) {
		ReservationMapper reservationMapper = sqlSession.getMapper(ReservationMapper.class);
		Map<String, String> map = new HashMap();
		map.put("rs_id", String.valueOf(rs_id));
		map.put("rs_user_id", rs_user_id);
		return reservationMapper.cancelReservation(map);
	}
	
	public List<ReservationTimeDto> getMobilityReservationTime(int rs_mobility_id){
		ReservationMapper mapper = sqlSession.getMapper(ReservationMapper.class);
		return mapper.getMobilityReservationTime(rs_mobility_id);
	}
}
