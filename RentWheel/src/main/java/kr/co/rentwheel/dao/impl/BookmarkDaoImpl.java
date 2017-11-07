package kr.co.rentwheel.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.rentwheel.dao.BookmarkDao;
import kr.co.rentwheel.dto.BookmarkDto;
import kr.co.rentwheel.dto.MobilityDto;
import kr.co.rentwheel.mapper.BookmarkMapper;

@Repository
public class BookmarkDaoImpl implements BookmarkDao{
	@Autowired
	private SqlSession sqlSession;
	
	public List<MobilityDto> getBookmark(String bm_user_id){
		BookmarkMapper mapper = sqlSession.getMapper(BookmarkMapper.class);
		return mapper.getBookmark(bm_user_id);
	}
	
	public void insertBookmark(BookmarkDto dto) {
		BookmarkMapper mapper = sqlSession.getMapper(BookmarkMapper.class);
		mapper.insertBookmark(dto);
	}
	
	public int deleteBookmark(BookmarkDto dto) {
		BookmarkMapper mapper = sqlSession.getMapper(BookmarkMapper.class);
		return mapper.deleteBookmark(dto);
	}
	public int updateBookmarkFlag(BookmarkDto dto) {
		BookmarkMapper mapper = sqlSession.getMapper(BookmarkMapper.class);
		return mapper.updateBookmarkFlag(dto);
	}
}
