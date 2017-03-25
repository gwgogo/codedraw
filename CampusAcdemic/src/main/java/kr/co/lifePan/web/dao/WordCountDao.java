package kr.co.lifePan.web.dao;

import java.util.List;

import kr.co.lifePan.web.domain.Community;
import kr.co.lifePan.web.domain.WordTable;

public interface WordCountDao {
	public List<WordTable> getData(Integer communityNo);
	
}
