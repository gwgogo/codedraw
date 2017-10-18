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

	/* �����Ƽ ����Ʈ ��ȸ (c_id �Ķ���Ͱ� ������ ��ü�� ������ �����Ʈ ����Ʈ, ������ ��ü �����Ƽ ����Ʈ)*/
	@RequestMapping(method = RequestMethod.GET)
	public List<MobilityDto> getMobilityByCompanyID(@RequestParam(value="c_id", required=false) String c_id) {
		if(c_id == null)
			return mobilityService.getMobilityAll();
		else
			return mobilityService.getMobilityByCompanyID(c_id);
	}
	
	/* Ư�� �����Ƽ ��ȸ */
	@RequestMapping(value = "/{m_id}", method = RequestMethod.GET)
	public MobilityDto getMobilityByMobilityID(@PathVariable("m_id") int m_id) {
		
		return mobilityService.getMobilityByMobilityID(m_id);
	}
	
	
	/* �ű� �����Ƽ ��ȸ , ���� ��¥�κ��� ������ �̳��� ��ϵ� �����Ƽ�� ��ȸ */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public List<MobilityDto> getNewMobility() {
		return mobilityService.getNewMobility();
	}
	
	/* ��õ �����Ƽ ��ȸ  - ���Ƿ� 10, 11, 12, 13�� �����Ÿ� ��õ �����ŷ� ����*/
	@RequestMapping(value = "/recommend", method = RequestMethod.GET)
	public List<MobilityDto> getRecommendMobility() {
		return mobilityService.getRecommendMobility();
	}
	
	/* ���� ���͸� �� �����Ƽ ��ȸ  min : �ּ� �ݾ�, max : �ִ�ݾ�, grade : 1(��), 2(��), 3(��)*/
	@RequestMapping(value = "/filter", method = RequestMethod.GET)
	public List<MobilityDto> getPriceFilterMobility(@RequestParam("min")int min, @RequestParam("max")int max, @RequestParam("grade")int grade) throws CustomException {
		
		String strGrade = null;
		if(grade == 1)
			strGrade="��";
		else if(grade == 2)
			strGrade="��";
		else if(grade == 3)
			strGrade="��";
		
		return mobilityService.getPriceFilterMobility(min, max, strGrade);
	}
	
}
