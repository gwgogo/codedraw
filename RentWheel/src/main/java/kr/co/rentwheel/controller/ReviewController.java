package kr.co.rentwheel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.rentwheel.domain.CustomException;
import kr.co.rentwheel.domain.ResponseItem;
import kr.co.rentwheel.dto.ReviewDto;
import kr.co.rentwheel.service.ReviewService;

@RestController
@RequestMapping(value="/review")
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseItem addReview(@RequestParam("rv_msg")String rv_msg, @RequestParam("rv_score")int rv_score, @RequestParam("rv_user_name") String rv_user_name,
			@RequestParam("rv_mobility_name") String rv_mobility_name) throws CustomException {
		
		ReviewDto reviewDto = new ReviewDto();
		reviewDto.setRv_msg(rv_msg);
		reviewDto.setRv_score(rv_score);
		reviewDto.setRv_user_name(rv_user_name);
		reviewDto.setRv_mobility_name(rv_mobility_name);
		return reviewService.addReview(reviewDto);
		
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public List<ReviewDto> getReview(@RequestParam("m_id") int m_id){
		
		return reviewService.getReview(m_id);
	}
}
