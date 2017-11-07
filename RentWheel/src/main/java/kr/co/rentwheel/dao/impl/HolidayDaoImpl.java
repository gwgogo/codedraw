package kr.co.rentwheel.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.rentwheel.dao.HolidayDao;
import kr.co.rentwheel.dto.HolidayDto;
import kr.co.rentwheel.mapper.HolidayMapper;

@Repository
public class HolidayDaoImpl implements HolidayDao {
	@Autowired
	private SqlSession sqlSession;
	
	public List<HolidayDto> getCompanyHoliday(String h_company_id){
		HolidayMapper mapper = sqlSession.getMapper(HolidayMapper.class);
		return mapper.getCompanyHoliday(h_company_id);
	}
}
