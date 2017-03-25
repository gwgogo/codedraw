package kr.co.lifePan.web.service;

import java.util.List;

import kr.co.lifePan.web.domain.Community;
import kr.co.lifePan.web.domain.WordTable;

public interface WordCountService {
	public List<WordTable> getData(Integer communityNo);
	
}
