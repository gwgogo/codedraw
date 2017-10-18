package kr.co.rentwheel.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.rentwheel.domain.CustomException;
import kr.co.rentwheel.dto.MobilityDto;
import kr.co.rentwheel.service.MobilityService;

@RestController
@RequestMapping("/mobility")
public class MobilityController {

	private static final Logger logger = LoggerFactory.getLogger(MobilityController.class);
	
	@Autowired
	private MobilityService mobilityService;

	/* 모빌리티 리스트 조회 (c_id 파라미터가 있으면 업체가 보유한 모빌리트 리스트, 없으면 전체 모빌리티 리스트)*/
	@RequestMapping(method = RequestMethod.GET)
	public List<MobilityDto> getMobilityByCompanyID(@RequestParam(value="c_id", required=false) String c_id) {
		if(c_id == null)
			return mobilityService.getMobilityAll();
		else
			return mobilityService.getMobilityByCompanyID(c_id);
	}
	
	/* 특정 모빌리티 조회 */
	@RequestMapping(value = "/{m_id}", method = RequestMethod.GET)
	public MobilityDto getMobilityByMobilityID(@PathVariable("m_id") int m_id) {
		
		return mobilityService.getMobilityByMobilityID(m_id);
	}
	
	
	/* 신규 모빌리티 조회 , 현재 날짜로부터 일주일 이내에 등록된 모빌리티만 조회 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public List<MobilityDto> getNewMobility() {
		return mobilityService.getNewMobility();
	}
	
	/* 추천 모빌리티 조회  - 임의로 10, 11, 12, 13번 자전거를 추천 자전거로 설정*/
	@RequestMapping(value = "/recommend", method = RequestMethod.GET)
	public List<MobilityDto> getRecommendMobility() {
		return mobilityService.getRecommendMobility();
	}
	
	/* 가격 필터링 한 모빌리티 조회  min : 최소 금액, max : 최대금액, grade : 1(상), 2(중), 3(하)*/
	@RequestMapping(value = "/filter", method = RequestMethod.GET)
	public List<MobilityDto> getPriceFilterMobility(@RequestParam("min")int min, @RequestParam("max")int max, @RequestParam("grade")int grade) throws CustomException {
		
		String strGrade = null;
		if(grade == 1)
			strGrade="상";
		else if(grade == 2)
			strGrade="중";
		else if(grade == 3)
			strGrade="하";
		
		return mobilityService.getPriceFilterMobility(min, max, strGrade);
	}
	
}
