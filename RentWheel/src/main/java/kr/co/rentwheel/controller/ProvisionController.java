package kr.co.rentwheel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.rentwheel.service.ProvisionService;


/*
 * p_flag
 * 0 : 이용약관
 * 1 : 개인정보취급방침
 * 2 : 대여약관
 * 3 : 오픈소스
 * 4 : 공공데이터
 * 5 : 보험약관
 * 
 */
@RestController
@RequestMapping(value="/provision")
public class ProvisionController {

	@Autowired
	private ProvisionService provisionService;
	
	@RequestMapping(method= RequestMethod.GET)
	public String getProvision(@RequestParam("p_flag")int p_flag) {
		String str = provisionService.getProvision(p_flag);
		System.out.println("str : " + str);
		return str;
	}
}
