package kr.co.rentwheel.service;

import java.util.List;

import kr.co.rentwheel.domain.ResponseItem;
import kr.co.rentwheel.dto.CompanyDto;

public interface CompanyService {
	public CompanyDto getCompanyByCompanyID(String c_id);
	public ResponseItem companyJoin(CompanyDto dto);
	public List<CompanyDto> getCompanyByAreaID(int c_area_id);
	public List<CompanyDto> getCompanyAll();
	public List<CompanyDto> getCompanyByRegionID(int c_area_id);
}
