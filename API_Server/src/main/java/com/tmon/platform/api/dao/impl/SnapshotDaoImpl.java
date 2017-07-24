package com.tmon.platform.api.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tmon.platform.api.dao.SnapShotDao;
import com.tmon.platform.api.dto.SnapShotDto;

/**
 * SnapShotDaoImpl
 * 
 * @author 구도원
 *
 *         관리자만 접근 가능한 Data Access Object Implement
 *
 */
@Repository
public class SnapshotDaoImpl implements SnapShotDao {

	@Autowired
	SqlSession sqlSession;

	@Override
	public List<SnapShotDto> selectBysnapshot_time(Map<String, Date> betweenTime) {
		return sqlSession.selectList("SnapShotMapper.selectBysnapshot_time", betweenTime);
	}

}
