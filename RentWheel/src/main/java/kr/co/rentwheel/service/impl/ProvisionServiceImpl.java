package kr.co.rentwheel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.rentwheel.dao.ProvisionDao;
import kr.co.rentwheel.service.ProvisionService;

@Service
public class ProvisionServiceImpl implements ProvisionService{

	@Autowired
	private ProvisionDao provisionDao;
	
	public String getProvision(int p_flag) {
		String p_msg = provisionDao.getProvision(p_flag);
		
		return p_msg;
			
	}
}
