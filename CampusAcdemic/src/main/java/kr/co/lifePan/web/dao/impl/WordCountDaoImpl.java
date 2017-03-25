package kr.co.lifePan.web.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.lifePan.web.dao.WordCountDao;
import kr.co.lifePan.web.domain.Community;
import kr.co.lifePan.web.domain.WordTable;
import kr.co.lifePan.web.persistence.mysql.mybatis.mapper.WordCountMapper;

@Component
public class WordCountDaoImpl implements WordCountDao{
	@Autowired
	private WordCountMapper wordCountMapper;
	
	@Override
	public List<WordTable> getData(Integer communityNo) {
		return wordCountMapper.getData(communityNo);
	}

	
}
