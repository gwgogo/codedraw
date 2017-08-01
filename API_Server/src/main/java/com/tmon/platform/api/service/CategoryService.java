package com.tmon.platform.api.service;

import java.util.List;

import com.tmon.platform.api.dto.CategoryDto;
import com.tmon.platform.api.exception.NullCustomException;

public interface CategoryService {
	public List<CategoryDto> categories() throws NullCustomException;
}
