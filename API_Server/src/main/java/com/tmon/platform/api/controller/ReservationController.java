package com.tmon.platform.api.controller;

import java.util.List;

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

import com.tmon.platform.api.dto.OrderProductDto;
import com.tmon.platform.api.dto.ReservationDto;
import com.tmon.platform.api.dto.TimeSlotDto;
import com.tmon.platform.api.exception.CustomException;
import com.tmon.platform.api.service.ReservationService;
import com.tmon.platform.api.util.SessionManager;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

class ReservationRequestDto {
	int timeslot_id;
	List<OrderProductDto> orderProductDto;
	public int getTimeslot_id() {
		return timeslot_id;
	}
	public void setTimeslot_id(int timeslot_id) {
		this.timeslot_id = timeslot_id;
	}
	public List<OrderProductDto> getOrderProductDto() {
		return orderProductDto;
	}
	public void setOrderProductDto(List<OrderProductDto> orderProductDto) {
		this.orderProductDto = orderProductDto;
	}
}

@CrossOrigin
@RestController
@RequestMapping("/orders")
public class ReservationController {
	private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);
	
	
	@Autowired 
	private ReservationService reservationService;
	
	@Autowired
	private SessionManager sessionManager;
	
	
	@ApiOperation(value="주문 완료 ", notes="주문 완료 버튼 클릭시 주문 테이블에 데이터 삽입")
	@ApiImplicitParam(name = "requestBody", value = "{ timeslot_id : value } , [{상품ID : value, 수량 : value}] } ", dataType = "string", paramType = "body")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "{msg : Success Insert Reservation}"),
            @ApiResponse(code = 501, message = "{msg : Fail Insert Reservation}")
    })
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public JSONObject addReservation(HttpServletRequest request,@RequestBody ReservationRequestDto requestBody) throws CustomException {
		
		String rawCookie = request.getHeader("Cookie");
		String session = sessionManager.getSession(rawCookie);
		String user_id = sessionManager.getUserId(session);
		
		int timeslot_id = requestBody.getTimeslot_id();
		List<OrderProductDto> orderProductDtoList = requestBody.getOrderProductDto();
		
		ReservationDto reservationDto = new ReservationDto();
		reservationDto.setUser_id(user_id);
		reservationDto.setTimeslot_id(timeslot_id);

		return reservationService.addReservation(reservationDto, orderProductDtoList);
	}
	
	
	@ApiOperation(value="주문 상태 변경 ", notes="0: 주문완료, 1: 배송중, 2: 배송완료,  3: 주문취소")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "reservation_id", value = "주문 ID", dataType = "int", paramType = "path"),
		@ApiImplicitParam(name = "status_id", value = "상태 번호", dataType = "int", paramType = "path")
	})
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "{msg : Success Update Reservation}"),
            @ApiResponse(code = 501, message = "{msg : Fail Update StatusReservation}")
    })
	@RequestMapping(value="/{reservation_id}/status/{status_id}", method= {RequestMethod.PUT, RequestMethod.DELETE})
	@ResponseBody
	public JSONObject updateStatusReservation(@PathVariable("reservation_id")int reservation_id, @PathVariable("status_id") int status_id) throws Exception{
		
		return reservationService.updateStatusReservation(reservation_id, status_id);
	}
	
	
}
