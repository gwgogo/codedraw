package com.tmon.platform.api.service;

import java.util.List;

import com.tmon.platform.api.dto.SnapShotDto;
import com.tmon.platform.api.exception.DateFormatException;
import com.tmon.platform.api.exception.SQLCustomException;

/**
 * SnapShotService
 * 
 * @author 구도원
 * 
 *         관리자만 접근 가능한 SnapShot Service interface
 *
 */
public interface SnapShotService {

	public List<SnapShotDto> selectBysnapshotTime(String searchInitTime, String searchFinishTime)
			throws DateFormatException, SQLCustomException;
}
