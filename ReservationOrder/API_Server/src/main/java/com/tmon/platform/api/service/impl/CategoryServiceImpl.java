package com.tmon.platform.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmon.platform.api.dao.CategoryDao;
import com.tmon.platform.api.dto.CategoryDto;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao categoryDao;
	
	/**
	 * @author 신광원
	 * @description 카테고리 전체 목록 가져오기
	 */
	public List<CategoryDto> categories() throws NullCustomException {
		return categoryDao.categories();
	}

}
