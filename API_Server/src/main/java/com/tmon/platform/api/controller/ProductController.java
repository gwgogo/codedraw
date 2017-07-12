package com.tmon.platform.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmon.platform.api.domain.ResponseItem;
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
	
	@ApiOperation(value="주문번호에 따른 상품 목록 조회")
	@RequestMapping(value="/order", method=RequestMethod.GET)
	@ResponseBody
	public List<ProductDto> productByOrderId(@RequestParam("order_id") int order_id){
		return productService.productByOrderId(order_id);
	}
	
	@ApiOperation(value="카테고리에 따른 상품 목록 조회")
	@RequestMapping(value="/category", method=RequestMethod.GET)
	@ResponseBody
	public List<ProductDto> productByCategoryId(@RequestParam("category_id") int category_id){
		return productService.productByCategoryId(category_id);
	}
	
	
}
