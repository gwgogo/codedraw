package kr.co.rentwheel.mapper;

import java.util.List;

import kr.co.rentwheel.dto.CompanyDto;

public interface CompanyMapper {
	public CompanyDto getCompanyByCompanyID(String c_id);
	public boolean companyJoin(CompanyDto dto);
	public List<CompanyDto> getCompanyByAreaID(int c_area_id);
	public List<CompanyDto> getCompanyAll();
	public List<CompanyDto> getCompanyByRegionID(int c_area_id);
}
