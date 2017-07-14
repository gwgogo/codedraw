package com.tmon.platform.api.controller;

import java.sql.SQLException;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmon.platform.api.dto.ReservationProductDto;
import com.tmon.platform.api.dto.ProductDto;
import com.tmon.platform.api.service.ProductService;

import io.swagger.annotations.ApiOperation;


@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@ApiOperation(value="API서버 접속 화면")
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String index() {
		return "index";
	}
	
	@ApiOperation(value="상품 목록 전체 조회")
	@RequestMapping(value="/products", method=RequestMethod.GET)
	@ResponseBody
	public List<ProductDto> productAll(){
		return productService.productAll();
	}
	
	@ApiOperation(value="상품 상세 조회")
	@RequestMapping(value="/productDetail", method=RequestMethod.GET)
	@ResponseBody
	public ProductDto productByProductId(@RequestParam("product_id")int product_id) {
		return productService.productByProductId(product_id);
	}
	
	@ApiOperation(value="주문번호에 따른 상품 목록 조회")
	@RequestMapping(value="/reservation", method=RequestMethod.GET)
	@ResponseBody
	public List<ReservationProductDto> productByReservationId(@RequestParam("reservation_id") int reservation_id){
		return productService.productByReservationId(reservation_id);
	}
	
	@ApiOperation(value="카테고리에 따른 상품 목록 조회")
	@RequestMapping(value="/category", method=RequestMethod.GET)
	@ResponseBody
	public List<ProductDto> productByCategoryId(@RequestParam("category_id") int category_id){
		return productService.productByCategoryId(category_id);
	}
	
	@ApiOperation(value="상품 삭제")
	@RequestMapping(value="/productDelete")
	@ResponseBody
	public JSONObject productDelete(@RequestParam("product_id")int product_id) throws SQLException {
		return productService.productDelete(product_id);
	}
	
	
	
}
