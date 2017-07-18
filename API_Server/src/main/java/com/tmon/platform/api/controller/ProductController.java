package com.tmon.platform.api.controller;

import java.sql.SQLException;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmon.platform.api.dto.ProductDto;
import com.tmon.platform.api.dto.ReservationProductDto;
import com.tmon.platform.api.exception.CustomException;
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
@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@ApiOperation(value="API서버 접속 화면")
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String index() {
		return "index";
	}
	
	
	@ApiOperation(value="상품 전체 조회", notes="모든 상품 목록 조회")
	@RequestMapping(value="/products", method=RequestMethod.GET)
	@ResponseBody
	public List<ProductDto> productAll(){
		return productService.productAll();
	}
	
	
	@ApiOperation(value="상품 상세 조회", notes="상품ID에 대한 상품 상세 조회  by author 신광원")
	@ApiImplicitParam(name = "product_id", value = "상품 ID", dataType = "int", paramType = "query")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "{data : List<ProductDto>}"),
			@ApiResponse(code = 501, message = "{msg : Invalid ProductID}")
	})
	@RequestMapping(value="/productDetail", method=RequestMethod.GET)
	@ResponseBody
	public ProductDto productByProductId(@RequestParam("product_id")int product_id) throws CustomException {
		return productService.productByProductId(product_id);
	}
	
	
	@ApiOperation(value="주문번호에 따른 상품 목록 조회", notes="하나의 주문에 포함된 상품 목록 조회")
	@ApiImplicitParam(name = "reservation_id", value = "주문 ID", dataType = "int", paramType = "query")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "{data : List<ProductDto>}"),
			@ApiResponse(code = 501, message = "{msg : Invalid ReservationID}")
	})
	@RequestMapping(value="/reservation", method=RequestMethod.GET)
	@ResponseBody
	public List<ReservationProductDto> productByReservationId(@RequestParam("reservation_id") int reservation_id) throws CustomException{
		return productService.productByReservationId(reservation_id);
	}
	
	
	@ApiOperation(value="카테고리 번호에 따른 상품 목록 조회", notes="카테고리별 상품 조회를 위한 API")
	@ApiImplicitParam(name = "category_id", value = "카테고리 ID", dataType = "int", paramType = "query")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "{data : List<ProductDto>}"),
			@ApiResponse(code = 501, message = "{msg : Invalid CategoryID}")
	})
	@RequestMapping(value="/category", method=RequestMethod.GET)
	@ResponseBody
	public List<ProductDto> productByCategoryId(@RequestParam("category_id") int category_id) throws CustomException{
		return productService.productByCategoryId(category_id);
	}
	
	
	@ApiOperation(value="상품 삭제")
	@ApiImplicitParam(name = "product_id", value = "상품 ID", dataType = "int", paramType = "query")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "{msg : Success DeleteProduct}"),
			@ApiResponse(code = 501, message = "{msg : Fail : Delete SQL Error}")
	})
	@RequestMapping(value="/deleteProduct", method=RequestMethod.DELETE)
	@ResponseBody
	public JSONObject deleteProduct(@RequestParam("product_id")int product_id) throws SQLException {
		return productService.deleteProduct(product_id);
	}
	
	
}
