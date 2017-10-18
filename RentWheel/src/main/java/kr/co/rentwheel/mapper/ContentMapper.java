package kr.co.rentwheel.mapper;

import java.util.List;

import kr.co.rentwheel.dto.ContentDto;

public interface ContentMapper {
	public List<ContentDto> contents(String ct_category);
}
