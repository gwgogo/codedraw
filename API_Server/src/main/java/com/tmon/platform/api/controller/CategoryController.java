package com.tmon.platform.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tmon.platform.api.dto.CategoryDto;
import com.tmon.platform.api.service.CategoryService;

@CrossOrigin
@RestController
@RequestMapping("/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<CategoryDto> categories(){
		return categoryService.categories();
	}
	
	
}
