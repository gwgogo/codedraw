package com.tmon.platform.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmon.platform.api.dto.SnapShotDto;
import com.tmon.platform.api.service.SnapShotService;

import io.swagger.annotations.ApiOperation;

/**
 * SnapShotController
 * 
 * @author 구도원
 * 
 *         본 Controller는 관리자만을 위한 Controller입니다. 별도의 Interceptor를 구성하여 관리자가
 *         아닌경우에는 본 Controller에 접근하지 못하도록 막을 계획입니다.
 *
 */
@Controller
public class SnapShotController {

	private static final Logger logger = LoggerFactory.getLogger(SnapShotController.class);

	@Autowired
	SnapShotService snapShotService;

	/**
	 * @author 구도원
	 * @param search_init_time
	 * @param search_finish_time
	 * @return List<SnapShotDto> [JSON]
	 */
	@ApiOperation(value = "스냅샷 검색(작업중)")
	@RequestMapping(value = "/admin_select_snapshot_time", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public List<SnapShotDto> admin_select_snapshot_time(@RequestParam("search_init_time") String search_init_time,
			@RequestParam("search_finish_time") String search_finish_time) throws Exception {
		logger.info("This is admin_select_snapshot_time");
		return snapShotService.selectBysnapshot_time(search_init_time, search_finish_time);
	}

}
