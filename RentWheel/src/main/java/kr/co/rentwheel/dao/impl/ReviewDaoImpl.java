package kr.co.rentwheel.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.rentwheel.dao.ReviewDao;
import kr.co.rentwheel.dto.ReviewDto;
import kr.co.rentwheel.mapper.ReviewMapper;


@Repository
public class ReviewDaoImpl implements ReviewDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<ReviewDto> getReview(String rv_mobility_name){
		ReviewMapper mapper = sqlSession.getMapper(ReviewMapper.class);
		return mapper.getReview(rv_mobility_name);
	}
	
	public void addReview(ReviewDto reviewDto) {
		ReviewMapper mapper = sqlSession.getMapper(ReviewMapper.class);
		mapper.addReview(reviewDto);
	}
}
