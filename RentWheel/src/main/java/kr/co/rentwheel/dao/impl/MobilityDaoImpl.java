package kr.co.rentwheel.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.rentwheel.dao.MobilityDao;
import kr.co.rentwheel.dto.MobilityDto;
import kr.co.rentwheel.mapper.MobilityMapper;

@Repository
public class MobilityDaoImpl implements MobilityDao{
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<MobilityDto> mobilitySelect(String c_id){
		MobilityMapper mapper = sqlSession.getMapper(MobilityMapper.class);
		return mapper.mobilitySelect(c_id);
	}
	
	public List<MobilityDto> mobilitySelectAll(){
		MobilityMapper mapper = sqlSession.getMapper(MobilityMapper.class);
		return mapper.mobilitySelectAll();
	}
	
	public MobilityDto mobilityId(String m_id){
		MobilityMapper mapper = sqlSession.getMapper(MobilityMapper.class);
		return mapper.mobilityId(m_id);
	}
}
