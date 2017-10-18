package kr.co.rentwheel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.rentwheel.dto.ContentDto;
import kr.co.rentwheel.service.ContentService;


/*
 * ct_category
 * 1 : 메인 이벤트 배너
 * 2 : 메인 추천 컨텐츠 배너
 * 3 : 추천 코스
 * 
 */
@RestController
public class ContentController {
	@Autowired
	private ContentService contentService;
	
	/* 카테고리번호에 따른 이벤트배너, 추천배너 리스트 조회*/
	@RequestMapping(value = "/contents", method = RequestMethod.GET)
	public List<ContentDto> contents(@RequestParam("ct_category") String ct_category) {
		return contentService.contents(ct_category);
	}
}
