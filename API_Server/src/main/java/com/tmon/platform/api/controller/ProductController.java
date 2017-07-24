package com.tmon.platform.api.controller;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tmon.platform.api.dto.OrderProductDto;
import com.tmon.platform.api.dto.ProductDto;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.exception.SQLCustomException;
import com.tmon.platform.api.service.ProductService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


/**
 * 
 * @author 신광원
 * @description 상품 조회 API
 *
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
	@ApiImplicitParam(name = "product_id", value = "상품 ID", dataType = "int", paramType = "path")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "{data : List<ProductDto>}"),
			@ApiResponse(code = 501, message = "{msg : Invalid ProductID}")
	})
	@RequestMapping(value="/{product_id}", method=RequestMethod.GET)
	public ProductDto productByProductId(@PathVariable("product_id")int product_id) throws NullCustomException {
		return productService.productByProductId(product_id);
	}
	
	
	@ApiOperation(value="주문번호에 따른 상품 목록 조회", notes="하나의 주문에 포함된 상품 목록 조회")
	@ApiImplicitParam(name = "reservation_id", value = "주문 ID", dataType = "int", paramType = "path")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "{data : List<ProductDto>}"),
			@ApiResponse(code = 501, message = "{msg : Invalid ReservationID}")
	})
	@RequestMapping(value="/reservation/{reservation_id}", method=RequestMethod.GET)
	public List<OrderProductDto> productByReservationId(@PathVariable("reservation_id") int reservation_id) throws NullCustomException{
		return productService.productByReservationId(reservation_id);
	}
	
	
	@ApiOperation(value="카테고리 번호에 따른 상품 목록 조회", notes="카테고리별 상품 조회를 위한 API")
	@ApiImplicitParam(name = "category_id", value = "카테고리 ID", dataType = "int", paramType = "path")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "{data : List<ProductDto>}"),
			@ApiResponse(code = 501, message = "{msg : Invalid CategoryID}")
	})
	@RequestMapping(value="/category/{category_id}", method=RequestMethod.GET)
	public List<ProductDto> productByCategoryId(@PathVariable("category_id") int category_id) throws NullCustomException{
		return productService.productByCategoryId(category_id);
	}
	
	
	@ApiOperation(value="상품 삭제")
	@ApiImplicitParam(name = "product_id", value = "상품 ID", dataType = "int", paramType = "path")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "{msg : Success DeleteProduct}"),
			@ApiResponse(code = 501, message = "{msg : Fail : Delete SQL Error}")
	})
	@RequestMapping(value="/{product_id}", method=RequestMethod.DELETE)
	public JSONObject deleteProduct(@PathVariable("product_id")int product_id) throws SQLCustomException {
		return productService.deleteProduct(product_id);
	}
	
	
}
