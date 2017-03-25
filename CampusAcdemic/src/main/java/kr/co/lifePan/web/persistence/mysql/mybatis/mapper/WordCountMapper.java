package kr.co.lifePan.web.persistence.mysql.mybatis.mapper;

import java.util.List;

import kr.co.lifePan.web.domain.Community;
import kr.co.lifePan.web.domain.WordTable;

public interface WordCountMapper {
	public List<WordTable> getData(Integer communityNo);
	
}
