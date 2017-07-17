package com.tmon.platform.api.controller;

import java.sql.SQLException;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmon.platform.api.dto.BasketDto;
import com.tmon.platform.api.service.BasketService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller
public class BasketController {
	
	@Autowired
	BasketService basketService;
	

	@ApiOperation(value="장바구니에 상품 등록", notes="로그인한 사용자의 장바구니에 상품 등록")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user_id", value = "사용자 ID", dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "product_id", value = "상품 ID", dataType = "int", paramType = "query"),
		@ApiImplicitParam(name = "quantity", value = "상품 수량", dataType = "int", paramType = "query")
	})
	@RequestMapping(value="/addBasket", method=RequestMethod.POST)
	@ResponseBody
	public JSONObject addBasket(
			@RequestParam("user_id") String user_id,
			@RequestParam("product_id") int product_id,
			@RequestParam("quantity") int quantity) throws SQLException {
		BasketDto basketDto = new BasketDto();
		basketDto.setUser_id(user_id);
		basketDto.setProduct_id(product_id);
		basketDto.setQuantity(quantity);
		
		return basketService.addBasket(basketDto);
	}
	
	
	@ApiOperation(value="장바구니 조회", notes="사용자에 따른 장바구니 상품 조회")
	@ApiImplicitParam(name = "user_id", value="사용자 ID", dataType = "string", paramType="query")
	@RequestMapping(value="/basket", method=RequestMethod.GET)
	@ResponseBody
	public List<BasketDto> basket(@RequestParam("user_id")String user_id){
		return basketService.basket(user_id);
	}
	
	
	
}
