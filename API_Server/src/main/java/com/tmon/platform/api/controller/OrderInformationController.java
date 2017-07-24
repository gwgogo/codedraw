package com.tmon.platform.api.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tmon.platform.api.dto.OrderInformationDto;
import com.tmon.platform.api.exception.OrderInformationException;
import com.tmon.platform.api.service.OrderInformationService;
import com.tmon.platform.api.util.SessionManager;

import io.swagger.annotations.ApiOperation;

/**
 * OrderInformationController
 * 
 * @author 구도원
 * 
 *         본 Controller는 관리자만을 위한 Controller입니다. 별도의 Interceptor를 구성하여 관리자가
 *         아닌경우에는 본 Controller에 접근하지 못하도록 막을 계획입니다.
 *
 */
@RestController
@RequestMapping(value = "/orders")
@CrossOrigin
public class OrderInformationController {

	private static final Logger logger = LoggerFactory.getLogger(OrderInformationController.class);

	@Autowired
	OrderInformationService orderInformationService;

	@Autowired
	private SessionManager sessionManager;

	@ApiOperation(value = "본인의 주문현황을 조회하는 API입니다. 검색날짜[yyyy-MM-dd](작업중)")
	@RequestMapping(method = RequestMethod.GET)
	public List<OrderInformationDto> select_orderinformation_mine(HttpServletRequest request,
			@RequestParam("search_init_date") String search_init_date,
			@RequestParam("search_finish_date") String search_finish_date) throws OrderInformationException {
		logger.info("This is select_orderinformation_mine");

		// 쿠키를 얻어온다.
		String rawCookie = request.getHeader("Cookie");

		// 세션을 얻는다.
		String session = sessionManager.getSession(rawCookie);

		// 해당 세션의 user_id를 얻는다.
		String user_id = sessionManager.getUserId(session);

		return orderInformationService.selectByDateAndUser(search_init_date, search_finish_date, user_id);
	}

	@ApiOperation(value = "주문현황을 조회하는 API입니다. 검색날짜[yyyy-MM-dd], 검색아이디 입력(작업중)")
	@RequestMapping(value = "/{user_id}", method = RequestMethod.GET)
	public List<OrderInformationDto> admin_select_orderinformation_dateanduser(@PathVariable("user_id") String user_id,
			@RequestParam("search_init_date") String search_init_date,
			@RequestParam("search_finish_date") String search_finish_date) throws OrderInformationException {
		logger.info("This is admin_select_orderinformation_dateanduser");

		return orderInformationService.selectByDateAndUser(search_init_date, search_finish_date, user_id);
	}

	@ApiOperation(value = "주문현황을 조회하는 API입니다. 검색날짜[yyyy-MM-dd] 입력(작업중)")
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<OrderInformationDto> admin_select_orderinformation_date(
			@RequestParam("search_init_date") String search_init_date,
			@RequestParam("search_finish_date") String search_finish_date) throws OrderInformationException {
		logger.info("This is admin_select_orderinformation_date");

		return orderInformationService.selectByDate(search_init_date, search_finish_date);
	}

}
