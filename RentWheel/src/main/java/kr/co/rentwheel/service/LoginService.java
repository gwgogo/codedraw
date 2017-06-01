package kr.co.rentwheel.service;

import kr.co.rentwheel.domain.ResponseItem;
import kr.co.rentwheel.dto.UserDto;


public interface LoginService {
	public ResponseItem login(String u_id, String u_pw);
	public ResponseItem isExistId(String u_id);
	public ResponseItem userJoin(UserDto user);
}
