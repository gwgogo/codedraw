package kr.co.rentwheel.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public List<MobilityDto> getMobilityByCompanyID(String c_id){
		MobilityMapper mapper = sqlSession.getMapper(MobilityMapper.class);
		return mapper.getMobilityByCompanyID(c_id);
	}
	
	public List<MobilityDto> getMobilityAll(){
		MobilityMapper mapper = sqlSession.getMapper(MobilityMapper.class);
		return mapper.getMobilityAll();
	}
	
	public MobilityDto getMobilityByMobilityID(int m_id){
		MobilityMapper mapper = sqlSession.getMapper(MobilityMapper.class);
		return mapper.getMobilityByMobilityID(m_id);
	}
	public List<MobilityDto> getNewMobility() {
		MobilityMapper mapper = sqlSession.getMapper(MobilityMapper.class);
		return mapper.getNewMobility();
	}
	public List<MobilityDto> getRecommendMobility(){
		MobilityMapper mapper = sqlSession.getMapper(MobilityMapper.class);
		return mapper.getRecommendMobility();
	}
	
	public List<MobilityDto> getPriceFilterMobility(int min, int max, String grade){
		MobilityMapper mapper = sqlSession.getMapper(MobilityMapper.class);
		Map<String, String> map = new HashMap();
		map.put("min",String.valueOf(min));
		map.put("max",String.valueOf(max));
		map.put("grade", grade);
		return mapper.getPriceFilterMobility(map);
	}
	
	public String getMobilityName(int m_id) {
		MobilityMapper mapper = sqlSession.getMapper(MobilityMapper.class);
		return mapper.getMobilityName(m_id);
	}
}
