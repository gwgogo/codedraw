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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.tmon.platform.api.dto.OrderInformationDto;
import com.tmon.platform.api.exception.AbstractCustomException;
import com.tmon.platform.api.exception.AuthException;
import com.tmon.platform.api.exception.DateFormatException;
import com.tmon.platform.api.exception.NullCustomException;
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

@RestController
@RequestMapping(value = "/orders")
@CrossOrigin
public class OrderInformationController {

	private static final Logger logger = LoggerFactory.getLogger(OrderInformationController.class);

	static class OrderRequestBody {
		private int timeslotID;
		private boolean fromBasket;
		private List<Map<String, String>> productList;
		public int getTimeslotID() {
			return timeslotID;
		}
		public boolean getFromBasket() {
			return fromBasket;
		}
		public List<Map<String, String>> getProductList() {
			return productList;
		}
	}
	
	@Autowired
	private OrderInformationService orderInformationService;

	@Autowired
	private SessionManager sessionManager;

	@ApiOperation(value = "주문현황을 조회(날짜, userID(Cookie))", notes = "검색날짜[yyyy-MM-dd] 입력")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "searchInitDate", value = "조회 시작 날짜[yyyy-MM-dd]", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "searchFinishDate", value = "조회 끝 날짜[yyyy-MM-dd]", dataType = "String", paramType = "query") })
	@ApiResponses(value = { @ApiResponse(code=200, message = "Success Select ORDER_INFORMATION SQL"),
			@ApiResponse(code = 615, message = "Fail Select ORDER_INFORMATION By Date And User Error"),
			@ApiResponse(code = 616, message = "Incorrect input Date data"),
			@ApiResponse(code = 616, message = "searchInitDate must be smaller than searchFinishDate"),
			@ApiResponse(code = 626, message = "There isn't USER (Not Valid)"),
			@ApiResponse(code = 623, message = "RequestParameterType Mismatch")})
	@RequestMapping(method = RequestMethod.GET)
	public List<OrderInformationDto> select_orderinformation_mine(HttpServletRequest request,
			@RequestParam("searchInitDate") String searchInitDate,
			@RequestParam("searchFinishDate") String searchFinishDate)
			throws DateFormatException, NullCustomException, MethodArgumentTypeMismatchException, AuthException, SQLCustomException {
		logger.info("This is select_orderinformation_mine");

		// 쿠키를 얻어온다.
		String rawCookie = request.getHeader("Cookie");

		// 세션을 얻는다.
		String session = sessionManager.getSession(rawCookie);

		// 해당 세션의 userID를 얻는다.
		String userID = sessionManager.getUserId(session);

		return orderInformationService.selectByDateAndUser(searchInitDate, searchFinishDate, userID);
	}

	@ApiOperation(value = "주문현황을 조회(날짜, ID)", notes = "검색날짜[yyyy-MM-dd], 검색아이디 입력")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "userID", value = "조회할 회원 ID", dataType = "String", paramType = "path"),
			@ApiImplicitParam(name = "searchInitDate", value = "조회 시작 날짜[yyyy-MM-dd]", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "searchFinishDate", value = "조회 끝 날짜[yyyy-MM-dd]", dataType = "String", paramType = "query") })
	@ApiResponses(value = { @ApiResponse(code = 200, message="Success Select ORDER_INFORMATION SQL"),
			@ApiResponse(code = 615, message = "Fail Select ORDER_INFORMATION By Date And User Error"),
			@ApiResponse(code = 616, message = "Incorrect input Date data"),
			@ApiResponse(code = 616, message = "searchInitDate must be smaller than searchFinishDate"),
			@ApiResponse(code = 626, message = "There isn't USER (Not Valid)"),
			@ApiResponse(code = 623, message = "RequestParameterType Mismatch")})
	@RequestMapping(value = "/admin/{userID}", method = RequestMethod.GET)
	public List<OrderInformationDto> admin_select_orderinformation_dateanduser(@PathVariable("userID") String userID,
			@RequestParam("searchInitDate") String searchInitDate,
			@RequestParam("searchFinishDate") String searchFinishDate)
			throws DateFormatException, SQLCustomException, NullCustomException, MethodArgumentTypeMismatchException {
		logger.info("This is admin_select_orderinformation_dateanduser");

		return orderInformationService.selectByDateAndUser(searchInitDate, searchFinishDate, userID);
	}

	@ApiOperation(value = "주문현황을 조회(날짜)", notes = "검색날짜[yyyy-MM-dd] 입력")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "searchInitDate", value = "조회 시작 날짜[yyyy-MM-dd]", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "searchFinishDate", value = "조회 끝 날짜[yyyy-MM-dd]", dataType = "String", paramType = "query") })
	@ApiResponses(value = { @ApiResponse(code =200, message="Success Select ORDER_INFORMATION SQL"),
			@ApiResponse(code = 615, message = "Fail Select ORDER_INFORMATION By Date Error"),
			@ApiResponse(code = 616, message = "Incorrect input Date data"),
			@ApiResponse(code = 616, message = "searchInitDate must be smaller than searchFinishDate"),
			@ApiResponse(code = 623, message = "RequestParameterType Mismatch")})
	@RequestMapping(value = "/admin/all", method = RequestMethod.GET)
	public List<OrderInformationDto> admin_select_orderinformation_date(
			@RequestParam("searchInitDate") String searchInitDate,
			@RequestParam("searchFinishDate") String searchFinishDate)
			throws DateFormatException, SQLCustomException, MethodArgumentTypeMismatchException {
		logger.info("This is admin_select_orderinformation_date");

		return orderInformationService.selectByDate(searchInitDate, searchFinishDate);
	}
	
	
	
	/**
	 * @author 신광원 
	 */
	@ApiOperation(value="주문 완료 ", notes="주문 완료 버튼 클릭시 주문 테이블에 데이터 삽입")
	@ApiImplicitParam(name = "requestBody", value = "{timeslotID:value, "
													+ "fromBasket:value,"
													+ "productList:"
													+ "[{productID:value, "
													+ "quantity:value}] } ", dataType = "string", paramType = "body")
	@ApiResponses(value = {
         @ApiResponse(code = 200, message = "Success Insert Reservation"),
         @ApiResponse(code = 610, message = "Fail : CutOff has been passed"),
         @ApiResponse(code = 611, message = "Fail : Insert Order SQL Error"),
         @ApiResponse(code = 612, message = "Fail : Insert OrderProduct SQL Error"),
         @ApiResponse(code = 616, message = "Can't increse TimeslotCount : Timeslot Full Count")
	})
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public JSONObject addOrder(HttpServletRequest request,@RequestBody OrderRequestBody requestBody) throws AbstractCustomException {
		
		String rawCookie = request.getHeader("Cookie");
		String session = sessionManager.getSession(rawCookie);
		String userID = sessionManager.getUserId(session);
		
		int timeslotID = requestBody.getTimeslotID();
		boolean fromBasket = requestBody.getFromBasket();		// 개별주문인지 장바구니 전체주문인지 구별하기 위한  true:장바구니 전체주문, false:바로주문
		List<Map<String, String>> productList = requestBody.getProductList();
		
		Map<String, String> orderDto = new HashMap<>();
		orderDto.put("userID", userID);
		orderDto.put("timeslotID", String.valueOf(timeslotID));

		orderInformationService.addOrder(orderDto, productList, fromBasket);
		
		JSONObject obj = new JSONObject();
		obj.put("msg", "Success Add Order");
		return obj;
	}
	
	
	
	/**
	 * @author 신광원 
	 */
	@ApiOperation(value="주문 취소 ", notes="0: 주문완료, 1: 배송중, 2: 배송완료,  3: 주문취소")	
	@ApiImplicitParam(name = "reservationID", value = "주문 ID", dataType = "int", paramType = "path")
	@ApiResponses(value = {
         @ApiResponse(code = 200, message = "Success Update Reservation"),
         @ApiResponse(code = 610, message = "Fail : CutOff has been passed"),
         @ApiResponse(code = 613, message = "Can't Delete Reservation : Unauthorized"),
         @ApiResponse(code = 614, message = "Invalid reservationID in ORDER_INFORMATION"),
         @ApiResponse(code = 614, message = "Invalid reservationID in RESERVATION_ORDER"),
         @ApiResponse(code = 615, message = "Can't cancel : OrderStatus is not ORDER_COMPLETE"),
         @ApiResponse(code = 616, message = "Can't decrese TimeslotCount : Now, Timeslot Count is Zero")
         })
	@RequestMapping(value="/{reservationID}", method= RequestMethod.DELETE)
	@ResponseBody
	public JSONObject cancelOrder(HttpServletRequest request, @PathVariable("reservationID")int reservationID) throws AbstractCustomException {
		String rawCookie = request.getHeader("Cookie");
		String session = sessionManager.getSession(rawCookie);
		
		return orderInformationService.cancelOrder(reservationID, session);
	}
	
}
