package com.tmon.platform.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmon.platform.api.dto.ProductDto;
import com.tmon.platform.api.service.ProductService;


@Controller
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String index() {
		return "index";
	}
	
	@RequestMapping(value="/selectProductListAll", method=RequestMethod.GET)
	@ResponseBody
	public List<ProductDto> selectProductListAll(){
		return productService.selectProductListAll();
	}

	@RequestMapping(value="/selectProductListByUserId", method=RequestMethod.GET)
	@ResponseBody
	public List<ProductDto> selectProductListByUserId(@RequestParam("user_id") String user_id){
		return productService.selectProductListByUserId(user_id);
	}
	
	@RequestMapping(value="/selectProductListByOrderId", method=RequestMethod.GET)
	@ResponseBody
	public List<ProductDto> selectProductListByOrderId(@RequestParam("order_id") int order_id){
		return productService.selectProductListByOrderId(order_id);
	}
	
	@RequestMapping(value="/selectProductListByCategoryId", method=RequestMethod.GET)
	@ResponseBody
	public List<ProductDto> selectProductListByCategoryId(@RequestParam("category_id") int category_id){
		return productService.selectProductListByCategoryId(category_id);
	}
}
