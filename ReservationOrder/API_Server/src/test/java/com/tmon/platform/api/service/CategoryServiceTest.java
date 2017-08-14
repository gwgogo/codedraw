package com.tmon.platform.api.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.tmon.platform.api.dto.CategoryDto;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.service.CategoryService;
/**
 * Category 조회 테스트
 * @author gwlee
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "file:src/test/resources/testServlet-context.xml",
		"file:src/test/resources/testDataSource-context.xml"})
public class CategoryServiceTest {

	@Autowired
	public CategoryService categoryService;

	@Test
	public void 그냥조회하기() throws NullCustomException {
		List<CategoryDto> list = categoryService.categories();
		
		for(CategoryDto category : list) {
			System.out.println(category.getCategoryID() + " : " + category.getCategoryName());
		}
	}
}
