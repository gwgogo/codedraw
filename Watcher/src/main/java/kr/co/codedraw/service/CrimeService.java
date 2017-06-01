package kr.co.codedraw.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import kr.co.codedraw.dto.CrimeCountDto;
import kr.co.codedraw.dto.CrimeDto;
import kr.co.codedraw.dto.CrimeMonthRateDto;

@Service
public interface CrimeService {
	public List<CrimeDto> crimeCountList(CrimeCountDto crimeCountDto);
	public List<CrimeMonthRateDto> crimePIchart(@Param("CrimeCountDto") CrimeCountDto crimeCountDto);
	
}
