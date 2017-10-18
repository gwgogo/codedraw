package kr.co.rentwheel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.rentwheel.dao.CompanyDao;
import kr.co.rentwheel.domain.ResponseItem;
import kr.co.rentwheel.dto.CompanyDto;
import kr.co.rentwheel.service.CompanyService;

@Service
public class CompanyServiceImpe implements CompanyService {

	@Autowired
	private CompanyDao companyDao;

	public CompanyDto getCompanyByCompanyID(String c_id) {
		return companyDao.getCompanyByCompanyID(c_id);
	}

	public ResponseItem companyJoin(CompanyDto dto){
		ResponseItem responseItem = new ResponseItem();
		try {
			companyDao.companyJoin(dto); 
		}catch(Exception e) {
			responseItem.setMsg("Fail : companyJoin Error");
			responseItem.setResult("407");
		}
		return responseItem;
	}

	public List<CompanyDto> getCompanyAll() {
		return companyDao.getCompanyAll();
	}

	public List<CompanyDto> getCompanyByAreaID(int c_area_id) {
		return companyDao.getCompanyByAreaID(c_area_id);
	}
	
	public List<CompanyDto> getCompanyByRegionID(int c_area_id){
		return companyDao.getCompanyByRegionID(c_area_id);
	}
}
