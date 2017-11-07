package kr.co.rentwheel.dao;

import java.util.List;

import kr.co.rentwheel.dto.CompanyDto;

public interface CompanyDao {
	public CompanyDto getCompanyByCompanyID(String c_id);
	public void companyJoin(CompanyDto dto);
	public List<CompanyDto> getCompanyByAreaID(int c_area_id);
	public List<CompanyDto> getCompanyAll();
	public List<CompanyDto> getCompanyByRegionID(int c_area_id);
}
