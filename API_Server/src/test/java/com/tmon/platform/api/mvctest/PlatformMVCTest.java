package com.tmon.platform.api.mvctest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * PlatformMVCTest
 * 
 * @author 구도원
 * 
 *         PlatformMVCTest는 WebApplicationContext를 통째로 가져와서 Mock을 생성하여 테스트 할 수
 *         있습니다. 즉, web.xml에 설정된 정보들을 바탕으로 Mock을 만듭니다. Dispatcher의 전체적인 구조에 대해서
 *         테스트를 할 수 있습니다.(단 filter는 Dispatcher접근 전 단계이므로 제외되고 Handler
 *         Interceptor는 유효합니다.)
 * 
 *         PlatformMVCTest를 상속받아서 사용하시면 다른 설정 필요없이 바로 @Test 코드를 작성하시면 됩니다.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/config/dataSource-context.xml",
		"file:src/main/webapp/WEB-INF/config/dateFormat-context.xml",
		"file:src/main/webapp/WEB-INF/config/servlet-context.xml" })
@WebAppConfiguration
public class PlatformMVCTest {

	/*
	 * @WebAppConfiguration을 통해 web.xml 환경설정을 사용하고 그것에 근거한 WebApplicationContext를
	 * 가져온다. 그리고 이것을 MockMvc객체에 사용한다.
	 */
	@Autowired
	protected WebApplicationContext wac;

	protected MockMvc mockMvc;

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Before
	public void mvcSetup() {
		logger.info("MVC Setup");
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void test() throws Exception {
		logger.info("MVC test example");
		mockMvc.perform(get("/bbb")).andExpect(status().isNotFound());
	}

	@After
	public void mvcSetdown() {
		logger.info("MVC test finish");
		mockMvc = null;
	}
}
