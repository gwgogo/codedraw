package com.tmon.platform.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tmon.platform.api.dto.CategoryDto;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.service.CategoryService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@RestController
@RequestMapping("/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	
	@ApiOperation(value="카테고리 조회", notes="카테고리ID에 따른 카테고리 리스트 조회")
	@ApiResponses(value = {
         @ApiResponse(code = 200, message = "data : List<CateogryDto>"),
         @ApiResponse(code = 619, message = "Fail : Invalid CategoryID")
	})
	@RequestMapping(method=RequestMethod.GET)
	public List<CategoryDto> categories() throws NullCustomException{
		return categoryService.categories();
	}
	
	
}
