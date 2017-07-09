package kr.co.codedraw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.codedraw.dao.CrimeDao;
import kr.co.codedraw.dto.CrimeCountDto;
import kr.co.codedraw.dto.CrimeDto;
import kr.co.codedraw.dto.CrimeMonthRateDto;
import kr.co.codedraw.service.CrimeService;

@Service
public class CrimeServiceImpe implements CrimeService{

	@Autowired
	private CrimeDao crimeDao;
	
	@Override
	public List<CrimeDto> crimeCountList(CrimeCountDto crimeCountDto) {
		return crimeDao.crimeCountList(crimeCountDto);
	}

	@Override
	public List<CrimeMonthRateDto> crimePIchart(CrimeCountDto crimeCountDto) {
		return crimeDao.crimePIchart(crimeCountDto);
	}

}
