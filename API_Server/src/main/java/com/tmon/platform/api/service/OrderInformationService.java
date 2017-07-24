package com.tmon.platform.api.service;

import java.util.List;

import com.tmon.platform.api.dto.OrderInformationDto;
import com.tmon.platform.api.exception.OrderInformationException;

/**
 * OrderInformationService
 * 
 * @author 구도원
 * 
 *         관리자만 접근 가능한 SnapShot Service interface
 *
 */
public interface OrderInformationService {

	public List<OrderInformationDto> selectByDate(String search_init_date, String search_finish_date)
			throws OrderInformationException;

	public List<OrderInformationDto> selectByDateAndUser(String search_init_date, String search_finish_date,
			String user_id) throws OrderInformationException;

}
