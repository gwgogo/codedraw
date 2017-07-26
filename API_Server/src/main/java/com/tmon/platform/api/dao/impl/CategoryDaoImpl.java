package com.tmon.platform.api.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tmon.platform.api.dao.CategoryDao;
import com.tmon.platform.api.dto.CategoryDto;

@Repository
public class CategoryDaoImpl implements CategoryDao {

	@Autowired
	private SqlSession sqlSession;
	@Override
	public List<CategoryDto> categories() {	
		return sqlSession.selectList("CategoryMapper.getCategoryList");
	}

}
