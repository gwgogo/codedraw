package com.tmon.platform.api.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmon.platform.api.dao.SnapShotDao;
import com.tmon.platform.api.dto.SnapShotDto;
import com.tmon.platform.api.exception.DateFormatException;
import com.tmon.platform.api.exception.SQLCustomException;
import com.tmon.platform.api.service.SnapShotService;
import com.tmon.platform.api.util.UtilForDate;

/**
 * SnapShotServiceImple
 * 
 * @author 구도원
 * 
 *         관리자만 접근 가능한 SnapShot Service implement
 *
 */
@Service
public class SnapShotServiceImpl implements SnapShotService {

	private static final Logger logger = LoggerFactory.getLogger(SnapShotServiceImpl.class);

	@Autowired
	SnapShotDao snapShotDao;

	@Override
	public List<SnapShotDto> selectBysnapshotTime(String searchInitTime, String searchFinishTime)
			throws DateFormatException, SQLCustomException {

		Date convertedSearchInitTime = UtilForDate.convertToDate(searchInitTime, "yyyy-MM-dd HH");
		Date convertedSearchFinishTime = UtilForDate.convertToDate(searchFinishTime, "yyyy-MM-dd HH");

		// WHERE절 변수 입력을 위해 parameterType을 Map구조로 한다.
		Map<String, Date> parameters = new HashMap<String, Date>();

		// 스냅샷테이블에서 검색할 배치 시간 (시작 시간 조건, 끝 시간 조건)
		parameters.put("searchInitTime", convertedSearchInitTime);
		parameters.put("searchFinishTime", convertedSearchFinishTime);

		/* 입력된 날짜데이터의 유효성 검사 */
		validateTime(parameters);

		try {
			return snapShotDao.selectBysnapshotTime(parameters);
		} catch (Exception e) {
			throw new SQLCustomException(620, "Fail Select SNAPSHOT SQL Error, SnapShot select Error");
		}
	}

	/* Service로직을 수행할지 안할지 결정하는 메소드 */
	private void validateTime(Map<String, Date> parameters) throws DateFormatException {

		/* 시간데이터를 long타입으로 변경한다. */
		long searchInitDate = parameters.get("searchInitTime").getTime();
		long searchFinishDate = parameters.get("searchFinishTime").getTime();

		/* 시작시간이 끝시간 보다 작아야 한다. */
		int flag = UtilForDate.compareDate(searchInitDate, searchFinishDate);
		if (flag < 1) {
			logger.info("searchInitDate: " + parameters.get("searchInitTime"));
			logger.info("searchFinishDate: " + parameters.get("searchFinishTime"));
			throw new DateFormatException(616, "startTime must be smaller than endTime");
		}
	}

}
