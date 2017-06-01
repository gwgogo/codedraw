package kr.co.rentwheel.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.rentwheel.dao.ContentDao;
import kr.co.rentwheel.dto.ContentDto;
import kr.co.rentwheel.mapper.ContentMapper;

@Repository
public class ContentDaoImpl implements ContentDao{
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<ContentDto> contents(String ct_category){
		ContentMapper mapper = sqlSession.getMapper(ContentMapper.class);
		return mapper.contents(ct_category);
	}
}
