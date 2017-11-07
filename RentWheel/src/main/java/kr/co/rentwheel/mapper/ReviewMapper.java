package kr.co.rentwheel.mapper;

import java.util.List;

import kr.co.rentwheel.dto.ReviewDto;

public interface ReviewMapper {
	
	public List<ReviewDto> getReview(String rv_mobility_name);
	public void addReview(ReviewDto reviewDto);
}
