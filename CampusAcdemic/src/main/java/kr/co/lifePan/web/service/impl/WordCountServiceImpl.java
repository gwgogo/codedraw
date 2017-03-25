package kr.co.lifePan.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.lifePan.web.dao.WordCountDao;
import kr.co.lifePan.web.domain.Community;
import kr.co.lifePan.web.domain.WordTable;
import kr.co.lifePan.web.service.WordCountService;

@Component
public class WordCountServiceImpl implements WordCountService{
	@Autowired
	private WordCountDao wordCountDao;

	@Override
	public List<WordTable> getData(Integer communityNo) {
		List<WordTable> data = new ArrayList<WordTable>();
		data= wordCountDao.getData(communityNo);
		return data;
	}
	
	/*버블정렬
	WordTable tmp = new WordTable();
	for (int i = 0; i < wordCount.size() - 1; i++) {
		for (int j = 0; j < wordCount.size() - 1 - i; j++) {
			if (wordCount.get(j).getCount() < wordCount.get(j+1).getCount()) {
	          tmp = wordCount.get(j);
	          wordCount.set(j,wordCount.get(j + 1)) ;
	          wordCount.set(j + 1,tmp);
	        }
		}
	}*/
}

