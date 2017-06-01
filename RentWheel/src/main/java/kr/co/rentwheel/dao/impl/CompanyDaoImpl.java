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
	
	public CompanyDto companySelect(String c_id){
		CompanyMapper mapper = sqlSession.getMapper(CompanyMapper.class);
		CompanyDto companyDto = mapper.companySelect(c_id);
		if(companyDto == null)
			return null;
		return companyDto;
		
	}
	
	public boolean companyJoin(CompanyDto dto){
		CompanyMapper mapper = sqlSession.getMapper(CompanyMapper.class);
		try{
			mapper.companyJoin(dto);
		}catch(Exception e){
			log.info("company Join Error : " + e);
			return false;
		}
		return true;
	}
	
	public List<CompanyDto> companiesAll(){
		CompanyMapper mapper = sqlSession.getMapper(CompanyMapper.class);
		List<CompanyDto> list =mapper.companiesAll(); 
		return list;
		
	}
	
	public List<CompanyDto> companies(int c_area_id){
		CompanyMapper mapper = sqlSession.getMapper(CompanyMapper.class);
		List<CompanyDto> list =mapper.companies(c_area_id); 
		return list;
		
	}
	
}
