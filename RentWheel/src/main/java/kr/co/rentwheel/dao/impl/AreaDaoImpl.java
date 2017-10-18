package kr.co.rentwheel.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.rentwheel.dao.AreaDao;
import kr.co.rentwheel.dto.AreaDto;
import kr.co.rentwheel.mapper.AreaMapper;

@Repository
public class AreaDaoImpl implements AreaDao {
	@Autowired
	private SqlSession sqlSession;
	public List<AreaDto> getAreaList(){
		AreaMapper mapper = sqlSession.getMapper(AreaMapper.class);
		return mapper.getAreaList();
	}
}
