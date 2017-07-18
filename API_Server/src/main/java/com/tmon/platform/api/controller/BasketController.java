package com.tmon.platform.api.controller;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmon.platform.api.dto.BasketDto;
import com.tmon.platform.api.exception.CustomException;
import com.tmon.platform.api.service.BasketService;
import com.tmon.platform.api.util.SessionManager;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@Controller
public class BasketController {
	private static final Logger logger = LoggerFactory.getLogger(BasketController.class);
	
	@Autowired
	private BasketService basketService;
	
	@Autowired 
	private SessionManager sessionManger;
	

	@ApiOperation(value="장바구니에 상품 등록", notes="현재 로그인한 사용자의 세션을 통해 사용자의 장바구니에 상품 등록")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "product_id", value = "상품 ID", dataType = "int", paramType = "query"),
		@ApiImplicitParam(name = "quantity", value = "상품 수량", dataType = "int", paramType = "query")
	})
	@RequestMapping(value="/addBasket", method=RequestMethod.POST)
	@ResponseBody
	public JSONObject addBasket(HttpServletRequest request, @RequestParam("product_id") int product_id, @RequestParam("quantity") int quantity) throws SQLException {
		
		String strCk = request.getHeader("Cookie");
		String user_id = getUser_id(strCk);
		return basketService.addBasket(user_id, product_id, quantity);
	}
	
	
	@ApiOperation(value="장바구니 조회", notes="현재 로그인한 사용자의 세션을 통해 장바구니 상품 조회")
	@RequestMapping(value="/basket", method=RequestMethod.GET)
	@ResponseBody
	public List<BasketDto> basket(HttpServletRequest request){
		
		String user_id= getUser_id(request.getHeader("Cookie"));
		return basketService.basket(user_id);
	}
	
	@ApiOperation(value="장바구니 상품 제거")
	@RequestMapping(value="/removeBasket", method=RequestMethod.DELETE)
	@ResponseBody
	public JSONObject removeBasket(HttpServletRequest request, @RequestParam("product_id")int product_id) throws CustomException{
		String user_id = getUser_id(request.getHeader("Cookie"));
		return basketService.removeBasket(user_id, product_id);
	}
	
	@ApiOperation(value="장바구니 상품 수량 증가")
	@RequestMapping(value="/incQuantity", method=RequestMethod.PUT)
	@ResponseBody
	public JSONObject incQuantity(HttpServletRequest request, @RequestParam("product_id")int product_id) throws CustomException{
		String user_id = getUser_id(request.getHeader("Cookie"));
		return basketService.incQuantity(user_id, product_id);
	}
	
	@ApiOperation(value="장바구니 상품 수량 감소")
	@RequestMapping(value="/decQuantity", method=RequestMethod.PUT)
	@ResponseBody
	public JSONObject decQuantity(HttpServletRequest request, @RequestParam("product_id")int product_id) throws CustomException{
		String user_id = getUser_id(request.getHeader("Cookie"));
		return basketService.decQuantity(user_id, product_id);
	}
	
	
	/* Cookie 값으로부터 user_id 추출  */
	private String getUser_id(String rawCookie) {
		String session = sessionManger.getSession(rawCookie);
		String user_id = sessionManger.getUserId(session);
		logger.info(user_id);
		return user_id;
	}
	
}
