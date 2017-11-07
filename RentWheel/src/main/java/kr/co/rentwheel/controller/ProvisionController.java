package kr.co.rentwheel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.rentwheel.service.ProvisionService;


/*
 * p_flag
 * 0 : �̿���
 * 1 : ����������޹�ħ
 * 2 : �뿩���
 * 3 : ���¼ҽ�
 * 4 : ����������
 * 5 : ������
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
