package kr.co.rentwheel.service;

import java.util.List;

import kr.co.rentwheel.domain.CustomException;
import kr.co.rentwheel.domain.ResponseItem;
import kr.co.rentwheel.dto.ReviewDto;

public interface ReviewService {
	public List<ReviewDto> getReview(int m_id);
	public ResponseItem addReview(ReviewDto reviewDto) throws CustomException;
}
