package kr.co.rentwheel.service;

import java.util.List;

import kr.co.rentwheel.dto.ContentDto;

public interface ContentService {
	public List<ContentDto> contents(String ct_category);
}
