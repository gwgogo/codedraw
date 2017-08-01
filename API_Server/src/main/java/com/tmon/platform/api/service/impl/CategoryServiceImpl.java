package com.tmon.platform.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmon.platform.api.dao.CategoryDao;
import com.tmon.platform.api.dto.CategoryDto;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.exception.RangeNotSatisfyException;
import com.tmon.platform.api.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao categoryDao;
	
	@Override
	public List<CategoryDto> categories() throws NullCustomException {
		List<CategoryDto> list = categoryDao.categories(); 
		if(list.isEmpty())
			throw new NullCustomException(619, "Fail : Invalid CategoryID" );
		return  list;
	}

}
