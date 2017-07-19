package com.tmon.platform.api.service;

import java.util.List;

import com.tmon.platform.api.dto.SnapShotDto;

public interface SnapShotService {

	public List<SnapShotDto> selectBysnapshot_time(String search_init_time, String search_finish_time) throws Exception;
}
