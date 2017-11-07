package kr.co.rentwheel.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.rentwheel.dao.CompanyDao;
import kr.co.rentwheel.dto.CompanyDto;
import kr.co.rentwheel.mapper.CompanyMapper;

@Repository
public class CompanyDaoImpl implements CompanyDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	static Logger log = Logger.getLogger(CompanyDaoImpl.class);
	
	public CompanyDto getCompanyByCompanyID(String c_id){
		CompanyMapper mapper = sqlSession.getMapper(CompanyMapper.class);
		return mapper.getCompanyByCompanyID(c_id);
	}
	
	public void companyJoin(CompanyDto dto){
		CompanyMapper mapper = sqlSession.getMapper(CompanyMapper.class);
		mapper.companyJoin(dto);
	}
	
	public List<CompanyDto> getCompanyAll(){
		CompanyMapper mapper = sqlSession.getMapper(CompanyMapper.class);
		return mapper.getCompanyAll(); 
	}
	
	public List<CompanyDto> getCompanyByAreaID(int c_area_id){
		CompanyMapper mapper = sqlSession.getMapper(CompanyMapper.class);
		return mapper.getCompanyByAreaID(c_area_id); 
	}
	
	public List<CompanyDto> getCompanyByRegionID(int c_area_id){
		CompanyMapper mapper = sqlSession.getMapper(CompanyMapper.class);
		return mapper.getCompanyByRegionID(c_area_id);
	}
}
