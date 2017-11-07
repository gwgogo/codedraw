package kr.co.rentwheel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.rentwheel.dao.AreaDao;
import kr.co.rentwheel.dto.AreaDto;
import kr.co.rentwheel.service.AreaService;

@Service
public class AreaServiceImpl implements AreaService {
	@Autowired
	private AreaDao areaDao;
	public List<AreaDto> getAreaList(){
		return areaDao.getAreaList();
	}
}
