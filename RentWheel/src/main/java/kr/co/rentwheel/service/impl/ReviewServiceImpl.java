package kr.co.rentwheel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.rentwheel.dao.ReviewDao;
import kr.co.rentwheel.domain.CustomException;
import kr.co.rentwheel.domain.ResponseItem;
import kr.co.rentwheel.dto.ReviewDto;
import kr.co.rentwheel.service.MobilityService;
import kr.co.rentwheel.service.ReviewService;


@Service
public class ReviewServiceImpl implements ReviewService {
	@Autowired
	private ReviewDao reviewDao;
	
	@Autowired
	private MobilityService mobilityService;
	
	public List<ReviewDto> getReview(int m_id){
		String m_name = mobilityService.getMobilityName(m_id);
		return reviewDao.getReview(m_name);
		
	}
	
	public ResponseItem addReview(ReviewDto reviewDto) throws CustomException {
		ResponseItem responseItem = new ResponseItem();
		
		try {
			reviewDao.addReview(reviewDto);
		}catch(Exception e) {
			throw new CustomException("423","Fail : Insert Review Error");
		}
		
		return responseItem;
	}
}
