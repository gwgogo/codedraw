package com.tmon.platform.api.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.tmon.platform.api.exception.DateFormatException;
import com.tmon.platform.api.exception.SQLCustomException;
import com.tmon.platform.api.service.SnapShotService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "file:src/test/resources/testServlet-context.xml",
		"file:src/test/resources/testDataSource-context.xml"})
public class SnapshotServiceTest {
	@Autowired
	SnapShotService snapshotService;
	
	@Test
	public void 정상넣기() throws DateFormatException, SQLCustomException {
		snapshotService.selectBysnapshotTime("2017-07-19 00", "2017-07-31 23");
	}

	@Test(expected=DateFormatException.class)
	public void 이상한포맷넣기양쪽() throws DateFormatException, SQLCustomException {
		snapshotService.selectBysnapshotTime("테스트테스트", "테스트오른쪽");
	}
	
	@Test(expected=DateFormatException.class)
	public void 이상한포맷넣기왼쪽() throws DateFormatException, SQLCustomException {
		snapshotService.selectBysnapshotTime("테스트테스트", "2017-07-31 23");
	}
	
	@Test(expected=DateFormatException.class)
	public void 이상한포맷넣기오른쪽() throws DateFormatException, SQLCustomException {
		snapshotService.selectBysnapshotTime("2017-07-19 00", "테스트오른쪽");
	}

	@Test(expected=DateFormatException.class)
	public void 앞뒤바꿔넣기() throws DateFormatException, SQLCustomException {
		snapshotService.selectBysnapshotTime("2017-07-31 23","2017-07-19 00");
	}


}
