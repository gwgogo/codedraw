package com.tmon.platform.api.dao;

import java.util.List;
import java.util.Map;

import com.tmon.platform.api.dto.OrderInformationDto;

/**
 * OrderInformationDao
 * 
 * @author 구도원
 * 
 *         관리자가 주문현황을 검색할 수 있는 Data Access Object
 *
 */
public interface OrderInformationDao {

	public List<OrderInformationDto> selectByDate(Map<String, Object> betweenDate);

	public List<OrderInformationDto> selectByDateAndUser(Map<String, Object> paramValues);

}
