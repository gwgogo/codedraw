package kr.co.rentwheel.dao;

import java.util.List;

import kr.co.rentwheel.dto.ReviewDto;

public interface ReviewDao {
	public List<ReviewDto> getReview(String rv_mobility_name);
	public void addReview(ReviewDto reviewDto);
}
