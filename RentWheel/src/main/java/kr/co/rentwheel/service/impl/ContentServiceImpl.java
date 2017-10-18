package kr.co.rentwheel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.rentwheel.dao.ContentDao;
import kr.co.rentwheel.dto.ContentDto;
import kr.co.rentwheel.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService{
	@Autowired
	private ContentDao contentDao;
	
	public List<ContentDto> contents(String ct_category){
		return contentDao.contents(ct_category);
	}
}
