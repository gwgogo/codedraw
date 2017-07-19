package com.tmon.platform.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

@CrossOrigin
@Controller
public class ReservationController {
	private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);
	
	
	@Autowired 
	private ReservationService reservationService;
	
	@Autowired
	private SessionManager sessionManager;
	
	
	@ApiOperation(value="주문 완료 ", notes="주문 완료 버튼 클릭시 주문 테이블에 데이터 삽입")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "timeslot_id", value = "타임슬롯 ID", dataType = "int", paramType = "query"),
		@ApiImplicitParam(name = "list", value = "{상품ID , 수량}의 리스트 ", dataType = "JSONArray", paramType = "body")
	})
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "{msg : Success Insert Reservation}"),
            @ApiResponse(code = 501, message = "{msg : Fail Insert Reservation}")
    })
	@RequestMapping(value="/reservation", method=RequestMethod.POST)
	@ResponseBody
	public JSONObject addReservation(HttpServletRequest request,
			@RequestParam("timeslot_id") int timeslot_id, @RequestBody List<OrderProductDto> list) throws Exception {
		
		/*String rawCookie = request.getHeader("Cookie");
		String session = sessionManager.getSession(rawCookie);
		String user_id = sessionManager.getUserId(session);*/
		String user_id = "user0001";
		
		ReservationDto reservationDto = new ReservationDto();
		reservationDto.setUser_id(user_id);
		reservationDto.setTimeslot_id(timeslot_id);
		
		// RESERVATION_ORDER 테이블에 주문 등록
		int reservation_id = reservationService.addReservation(reservationDto);
		
		
		// ORDER_INFORMATION 테이블에 상품 등록
		for(int productIdx= 0; productIdx < list.size(); productIdx++) {
			// RESERVATION_ORDER 와 ORDER_INFORMATION의 주문번호 일치시킴
			list.get(productIdx).setReservation_id(reservation_id);			
			reservationService.addOrderProduct(list.get(productIdx));
		}
		
		JSONObject obj = new JSONObject();
		obj.put("msg", "Success Insert Reservation");
		
		return obj;
	}
	
	
	@RequestMapping(value="/updateStatusReservation", method=RequestMethod.PUT)
	@ResponseBody
	public JSONObject updateStatusReservation(@RequestParam("reservation_id")int reservation_id, @RequestParam("status_id") int status_id) throws Exception{
		return reservationService.updateStatusReservation(reservation_id, status_id);
	}
	
	
	@ApiOperation(value="타임슬롯 조회", notes="예약배송 가능한 타임슬롯 조회")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "{data : Valid TimeSlot}"),
            @ApiResponse(code = 501, message = "{msg : Not Exist Valid TimeSlot}")
    })
	@RequestMapping(value = "/validTimeSlot", method=RequestMethod.GET)
	@ResponseBody
	public List<TimeSlotDto> validTimeSlot() throws CustomException{
		return reservationService.validTimeSlot();
	}
	
}
