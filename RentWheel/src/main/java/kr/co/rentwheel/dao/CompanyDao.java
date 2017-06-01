package kr.co.rentwheel.dao;

import java.util.List;

import kr.co.rentwheel.dto.CompanyDto;

public interface CompanyDao {
	public CompanyDto companySelect(String c_id);
	public boolean companyJoin(CompanyDto dto);
	public List<CompanyDto> companies(int c_area_id);
	public List<CompanyDto> companiesAll();
}
