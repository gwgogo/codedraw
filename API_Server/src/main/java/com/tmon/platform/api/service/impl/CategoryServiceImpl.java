package com.tmon.platform.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmon.platform.api.dao.CategoryDao;
import com.tmon.platform.api.dto.CategoryDto;
import com.tmon.platform.api.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao categoryDao;
	
	@Override
	public List<CategoryDto> categories() {
		return categoryDao.categories();
	}

}
