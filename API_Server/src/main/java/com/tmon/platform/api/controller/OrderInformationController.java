package com.tmon.platform.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tmon.platform.api.dto.OrderInformationDto;
import com.tmon.platform.api.exception.AbstractCustomException;
import com.tmon.platform.api.exception.DateFormatException;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.exception.RangeNotSatisfyException;
import com.tmon.platform.api.exception.SQLCustomException;
import com.tmon.platform.api.service.OrderInformationService;
import com.tmon.platform.api.util.SessionManager;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * OrderInformationController
 * 
 * @author 구도원
 * @author 신광원
 * 
 */
class OrderRequestBody {
	private int timeslot_id;
	private List<Map<String, String>> productList;
	public int getTimeslot_id() {
		return timeslot_id;
	}
	public List<Map<String, String>> getProductList() {
		return productList;
	}
}

@RestController
@RequestMapping(value = "/orders")
@CrossOrigin
public class OrderInformationController {

	private static final Logger logger = LoggerFactory.getLogger(OrderInformationController.class);

	@Autowired
	private OrderInformationService orderInformationService;

	@Autowired
	private SessionManager sessionManager;

	@ApiOperation(value = "주문현황을 조회(날짜, user_id(Cookie))", notes = "검색날짜[yyyy-MM-dd] 입력")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "search_init_date", value = "조회 시작 날짜[yyyy-MM-dd]", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "search_finish_date", value = "조회 끝 날짜[yyyy-MM-dd]", dataType = "String", paramType = "query") })
	@ApiResponses(value = { @ApiResponse(code = 615, message = "Fail Select ORDER_INFORMATION By Date And User Error"),
			@ApiResponse(code = 616, message = "Incorrect input Date data"),
			@ApiResponse(code = 616, message = "search_init_date must be smaller than search_finish_date") })
	@RequestMapping(method = RequestMethod.GET)
	public List<OrderInformationDto> select_orderinformation_mine(HttpServletRequest request,
			@RequestParam("search_init_date") String search_init_date,
			@RequestParam("search_finish_date") String search_finish_date)
			throws DateFormatException, SQLCustomException {
		logger.info("This is select_orderinformation_mine");

		// 쿠키를 얻어온다.
		String rawCookie = request.getHeader("Cookie");

		// 세션을 얻는다.
		String session = sessionManager.getSession(rawCookie);

		// 해당 세션의 user_id를 얻는다.
		String user_id = sessionManager.getUserId(session);

		return orderInformationService.selectByDateAndUser(search_init_date, search_finish_date, user_id);
	}

	@ApiOperation(value = "주문현황을 조회(날짜, ID)", notes = "검색날짜[yyyy-MM-dd], 검색아이디 입력")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "user_id", value = "조회할 회원 ID", dataType = "String", paramType = "path"),
			@ApiImplicitParam(name = "search_init_date", value = "조회 시작 날짜[yyyy-MM-dd]", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "search_finish_date", value = "조회 끝 날짜[yyyy-MM-dd]", dataType = "String", paramType = "query") })
	@ApiResponses(value = { @ApiResponse(code = 615, message = "Fail Select ORDER_INFORMATION By Date And User Error"),
			@ApiResponse(code = 616, message = "Incorrect input Date data"),
			@ApiResponse(code = 616, message = "search_init_date must be smaller than search_finish_date") })
	@RequestMapping(value = "/admin/{user_id}", method = RequestMethod.GET)
	public List<OrderInformationDto> admin_select_orderinformation_dateanduser(@PathVariable("user_id") String user_id,
			@RequestParam("search_init_date") String search_init_date,
			@RequestParam("search_finish_date") String search_finish_date)
			throws DateFormatException, SQLCustomException {
		logger.info("This is admin_select_orderinformation_dateanduser");

		return orderInformationService.selectByDateAndUser(search_init_date, search_finish_date, user_id);
	}

	@ApiOperation(value = "주문현황을 조회(날짜)", notes = "검색날짜[yyyy-MM-dd] 입력")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "search_init_date", value = "조회 시작 날짜[yyyy-MM-dd]", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "search_finish_date", value = "조회 끝 날짜[yyyy-MM-dd]", dataType = "String", paramType = "query") })
	@ApiResponses(value = { @ApiResponse(code = 615, message = "Fail Select ORDER_INFORMATION By Date Error"),
			@ApiResponse(code = 616, message = "Incorrect input Date data"),
			@ApiResponse(code = 616, message = "search_init_date must be smaller than search_finish_date") })
	@RequestMapping(value = "/admin/all", method = RequestMethod.GET)
	public List<OrderInformationDto> admin_select_orderinformation_date(
			@RequestParam("search_init_date") String search_init_date,
			@RequestParam("search_finish_date") String search_finish_date)
			throws DateFormatException, SQLCustomException {
		logger.info("This is admin_select_orderinformation_date");

		return orderInformationService.selectByDate(search_init_date, search_finish_date);
	}
	
	
	@ApiOperation(value="장바구니 전체 주문 완료 ", notes="주문 완료 버튼 클릭시 주문 테이블에 데이터 삽입")
	@ApiImplicitParam(name = "requestBody", value = "{ timeslot : value } , [{상품ID : value, 수량 : value}] } ", dataType = "string", paramType = "body")
	@ApiResponses(value = {
         @ApiResponse(code = 200, message = "{msg : Success Insert Reservation}"),
         @ApiResponse(code = 501, message = "{msg : Fail Insert Reservation}")
	})
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public JSONObject addOrder(HttpServletRequest request,@RequestBody OrderRequestBody requestBody) throws AbstractCustomException {
		
		String rawCookie = request.getHeader("Cookie");
		String session = sessionManager.getSession(rawCookie);
		String user_id = sessionManager.getUserId(session);
		
		int timeslot_id = requestBody.getTimeslot_id();
		List<Map<String, String>> productList = requestBody.getProductList();
		
		Map<String, String> orderDto = new HashMap<>();
		orderDto.put("user_id", user_id);
		orderDto.put("timeslot_id", String.valueOf(timeslot_id));
		
		return orderInformationService.addOrder(orderDto, productList);
	}
	
	
	@ApiOperation(value="바로 구매(주문) 완료 ")	
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "product_id", value = "상품 ID", dataType = "int", paramType = "path"),
			@ApiImplicitParam(name = "quantity", value = "주문 수량", dataType = "int", paramType = "path")})
	@ApiResponses(value = {
         @ApiResponse(code = 200, message = "{msg : Success Update Reservation}"),
         @ApiResponse(code = 501, message = "{msg : Fail Update StatusReservation}")})
	@RequestMapping(value="/{product_id}/{quantity}", method= RequestMethod.POST)
	@ResponseBody
	public JSONObject addInstantOrder(
			HttpServletRequest request,
			@PathVariable("product_id")int product_id, 
			@PathVariable("quantity")int quantity,
			@RequestBody JSONObject requestBody ) throws RangeNotSatisfyException, SQLCustomException, NullCustomException {
		
		
		String rawCookie = request.getHeader("Cookie");
		String session = sessionManager.getSession(rawCookie);
		String user_id = sessionManager.getUserId(session);
		
		int timeslot_id = Integer.parseInt(requestBody.get("timeslot_id").toString());
		Map<String, String> orderDto = new HashMap<>();
		orderDto.put("user_id", user_id);
		orderDto.put("timeslot_id", String.valueOf(timeslot_id));
		
		return orderInformationService.addInstantOrder(orderDto, product_id, quantity);
	}
	
	
	
	@ApiOperation(value="주문 상태 변경 ", notes="0: 주문완료, 1: 배송중, 2: 배송완료,  3: 주문취소")	
	@ApiImplicitParam(name = "reservation_id", value = "주문 ID", dataType = "int", paramType = "path")
	@ApiResponses(value = {
         @ApiResponse(code = 200, message = "{msg : Success Update Reservation}"),
         @ApiResponse(code = 501, message = "{msg : Fail Update StatusReservation}")})
	@RequestMapping(value="/{reservation_id}", method= RequestMethod.DELETE)
	@ResponseBody
	public JSONObject cancelOrder(@PathVariable("reservation_id")int reservation_id) throws Exception{
		
		return orderInformationService.cancelOrder(reservation_id);
	}
	
}
