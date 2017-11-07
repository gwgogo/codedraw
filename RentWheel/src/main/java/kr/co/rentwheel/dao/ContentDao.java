package kr.co.rentwheel.dao;

import java.util.List;

import kr.co.rentwheel.dto.ContentDto;

public interface ContentDao {
	public List<ContentDto> contents(String ct_category);
}
