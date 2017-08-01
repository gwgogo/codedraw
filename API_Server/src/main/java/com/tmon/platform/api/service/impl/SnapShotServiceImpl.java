package com.tmon.platform.api.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

		SimpleDateFormat searchFormat = new SimpleDateFormat("yyyy-MM-dd HH");

		Date convertedSearchInitTime;
		Date convertedSearchFinishTime;
		try {
			convertedSearchInitTime = searchFormat.parse(searchInitTime);
			convertedSearchFinishTime = searchFormat.parse(searchFinishTime);
		} catch (ParseException e) {
			logger.info(e.toString());
			throw new DateFormatException(616, "Incorrect input Time or Date data");
		}

		// WHERE절 변수 입력을 위해 parameterType을 Map구조로 한다.
		Map<String, Date> betweenTime = new HashMap<String, Date>();

		// 스냅샷테이블에서 검색할 배치 시간 (검색 시작 조건)
		betweenTime.put("searchInitTime", convertedSearchInitTime);

		// 스냅샷테이블에서 검색할 배치 시간 (검색 끝 조건)
		betweenTime.put("searchFinishTime", convertedSearchFinishTime);

		// 검색 시작 날짜가 검색 끝 날짜 보다 작아야 한다.
		long initDate = ((Date) betweenTime.get("searchInitTime")).getTime();
		long finishDate = ((Date) betweenTime.get("searchFinishTime")).getTime();
		if (initDate > finishDate) {
			logger.info("searchStartTime: " + betweenTime.get("searchInitTime"));
			logger.info("searchEndTime: " + betweenTime.get("searchFinishTime"));
			throw new DateFormatException(616, "searchInitTime must be smaller than searchFinishTime");
		}

		try {
			return snapShotDao.selectBysnapshotTime(betweenTime);

		} catch (Exception e) {
			throw new SQLCustomException(620, "Fail Select SNAPSHOT SQL Error, SnapShot select Error");
		}
	}

}
