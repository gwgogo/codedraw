package com.tmon.platform.api.service;

import java.util.List;

import com.tmon.platform.api.dto.SnapShotDto;

/**
 * SnapShotService
 * 
 * @author 구도원
 * 
 *         관리자만 접근 가능한 SnapShot Service interface
 *
 */
public interface SnapShotService {

	public List<SnapShotDto> selectBysnapshot_time(String search_init_time, String search_finish_time) throws Exception;
}
