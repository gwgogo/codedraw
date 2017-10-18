package kr.co.rentwheel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.rentwheel.dto.AreaDto;
import kr.co.rentwheel.service.AreaService;

@RestController
@RequestMapping(value="/area")
public class AreaController {
	
	@Autowired
	private AreaService areaService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<AreaDto> getAreaList(){
		return areaService.getAreaList();
	}
	
}
