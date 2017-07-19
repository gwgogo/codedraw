package com.tmon.platform.api.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tmon.platform.api.dto.SnapShotDto;

public interface SnapShotDao {

	public List<SnapShotDto> selectBysnapshot_time(Map<String, Date> betweenTime);
}
