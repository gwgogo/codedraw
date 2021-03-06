package com.tmon.platform.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.tmon.platform.api.dto.ProductDto;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.service.ProductService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


/**
 * @author 신광원 
 */
@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	

	@ApiOperation(value="상품 전체 조회", notes="모든 상품 목록 조회")
	@RequestMapping(method=RequestMethod.GET)
	public List<ProductDto> productAll(){
		return productService.productAll();
	}
	
	
	@ApiOperation(value="상품 상세 조회", notes="상품ID에 대한 상품 상세 조회 ")
	@ApiImplicitParam(name = "productID", value = "상품 ID", dataType = "int", paramType = "path")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "data : List<ProductDto>"),
			@ApiResponse(code = 604, message = "Invalid productID"),
			@ApiResponse(code = 623, message = "RequestParameterType Mismatch")
	})
	@RequestMapping(value="/{productID}", method=RequestMethod.GET)
	public ProductDto productByProductId(@PathVariable("productID")int productID) throws NullCustomException, MethodArgumentTypeMismatchException {
		return productService.productByProductId(productID);
	}
	
	
	@ApiOperation(value="카테고리 번호에 따른 상품 목록 조회", notes="카테고리별 상품 조회를 위한 API")
	@ApiImplicitParam(name = "categoryID", value = "카테고리 ID", dataType = "int", paramType = "path")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "data : List<ProductDto>"),
			@ApiResponse(code = 604, message = "Invalid categoryID"),
			@ApiResponse(code = 623, message = "RequestParameterType Mismatch")
	})
	@RequestMapping(value="/category/{categoryID}", method=RequestMethod.GET)
	public List<ProductDto> productByCategoryID(@PathVariable("categoryID") int categoryID) throws NullCustomException, MethodArgumentTypeMismatchException {
		
		return productService.productByCategoryID(categoryID);
	}
	
	
}
