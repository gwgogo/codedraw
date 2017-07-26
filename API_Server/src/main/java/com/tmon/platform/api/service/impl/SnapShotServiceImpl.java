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
	public List<SnapShotDto> selectBysnapshot_time(String search_init_time, String search_finish_time)
			throws DateFormatException {

		SimpleDateFormat searchFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");

		Date converted_search_init_time;
		Date converted_search_finish_time;
		try {
			converted_search_init_time = searchFormat.parse(search_init_time);
			converted_search_finish_time = searchFormat.parse(search_finish_time);
		} catch (ParseException e) {
			logger.info(e.toString());
			throw new DateFormatException(500, "incorrect input Time or Date data");
		}

		// WHERE절 변수 입력을 위해 parameterType을 Map구조로 한다.
		Map<String, Date> betweenTime = new HashMap<String, Date>();

		// 스냅샷테이블에서 검색할 배치 시간 (검색 시작 조건)
		betweenTime.put("search_init_time", converted_search_init_time);

		// 스냅샷테이블에서 검색할 배치 시간 (검색 끝 조건)
		betweenTime.put("search_finish_time", converted_search_finish_time);

		// 검색 시작 날짜가 검색 끝 날짜 보다 작아야 한다.
		long initDate = ((Date) betweenTime.get("search_init_time")).getTime();
		long finishDate = ((Date) betweenTime.get("search_finish_time")).getTime();
		if (initDate > finishDate) {
			logger.info("search_start_time: " + betweenTime.get("search_init_time"));
			logger.info("search_end_time: " + betweenTime.get("search_finish_time"));
			throw new DateFormatException(500, "search_init_time must be smaller than search_finish_time");
		}

		// Dao에서는 Map객체를 parameter로 받는다.
		return snapShotDao.selectBysnapshot_time(betweenTime);
	}

}
