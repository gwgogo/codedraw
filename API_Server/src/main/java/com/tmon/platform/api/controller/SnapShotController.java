package com.tmon.platform.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tmon.platform.api.dto.SnapShotDto;
import com.tmon.platform.api.exception.DateFormatException;
import com.tmon.platform.api.exception.SQLCustomException;
import com.tmon.platform.api.service.SnapShotService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * SnapShotController
 * 
 * @author 구도원
 * 
 *         본 Controller는 관리자만을 위한 Controller입니다. 별도의 Interceptor를 구성하여 관리자가
 *         아닌경우에는 본 Controller에 접근하지 못하도록 막을 계획입니다.
 *
 */
@RestController
@RequestMapping(value = "/snapshots")
@CrossOrigin
public class SnapShotController {

	private static final Logger logger = LoggerFactory.getLogger(SnapShotController.class);

	@Autowired
	SnapShotService snapShotService;

	@ApiOperation(value = "스냅샷 조회", notes = "지정한 날짜와 시간 범위에 해당하는 스냅샷을 조회할 수 있습니다.")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "searchInitTime", value = "조회 시작 시간[yyyy-MM-dd HH:mm:ss]", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "searchFinishTime", value = "조회 끝 시간[yyyy-MM-dd HH:mm:ss]", dataType = "String", paramType = "query") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success Select SNAPSHOT SQL"),
			@ApiResponse(code = 616, message = "Incorrect input Time or Date data"),
			@ApiResponse(code = 616, message = "searchInitTime must be smaller than searchFinishTime") })
	@RequestMapping(method = RequestMethod.GET)
	public List<SnapShotDto> adminSelectSnapshotTime(@RequestParam("searchInitTime") String searchInitTime,
			@RequestParam("searchFinishTime") String searchFinishTime) throws DateFormatException, SQLCustomException {
		logger.info("This is adminSelectSnapshotTime");

		return snapShotService.selectBysnapshotTime(searchInitTime, searchFinishTime);
	}

}
