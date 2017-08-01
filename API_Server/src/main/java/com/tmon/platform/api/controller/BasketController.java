package com.tmon.platform.api.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tmon.platform.api.dto.BasketDto;
import com.tmon.platform.api.exception.AbstractCustomException;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.service.BasketService;
import com.tmon.platform.api.util.SessionManager;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@RestController
@RequestMapping("/baskets")
public class BasketController {
	private static final Logger logger = LoggerFactory.getLogger(BasketController.class);
	
	@Autowired
	private BasketService basketService;
	
	@Autowired 
	private SessionManager sessionManger;
	

	@ApiOperation(value="장바구니에 상품 등록", notes="현재 로그인한 사용자의 세션을 통해 사용자의 장바구니에 상품 등록")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "productID", value = "상품 ID", dataType = "int", paramType = "path"),
		@ApiImplicitParam(name = "quantity", value = "상품 수량", dataType = "int", paramType = "path")
	})
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success Insert Basket"),
            @ApiResponse(code = 608, message = "Fail : Insert Basket SQL Error")
    })
	@RequestMapping(value="/{productID}/{quantity}", method=RequestMethod.POST)
	public JSONObject addBasket(HttpServletRequest request, @PathVariable("productID") int productID, @PathVariable("quantity") int quantity) throws AbstractCustomException {
		String strCk = request.getHeader("Cookie");
		String userID = getUserID(strCk);
		return basketService.addBasket(userID, productID, quantity);
	}
	
	
	@ApiOperation(value="장바구니 조회", notes="현재 로그인한 사용자의 세션을 통해 장바구니 상품 조회")
	@RequestMapping(method=RequestMethod.GET)
	public List<BasketDto> basket(HttpServletRequest request) throws NullCustomException{
		
		String userID= getUserID(request.getHeader("Cookie"));
		return basketService.basket(userID);
	}
	
	
	@ApiOperation(value="장바구니 상품 제거")
	@ApiImplicitParam(name = "productID", value = "상품 ID", dataType = "int", paramType = "path")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success Remove Basket"),
            @ApiResponse(code = 609, message = "Deleted Zero RowCount : Invalid productID")
    })
	@RequestMapping(value="/{productID}", method=RequestMethod.DELETE)
	public JSONObject removeBasket(HttpServletRequest request, @PathVariable("productID")int productID) throws AbstractCustomException{
		String userID = getUserID(request.getHeader("Cookie"));
		return basketService.removeBasket(userID, productID);
	}
	
	
	@ApiOperation(value="장바구니 초기화")
    @ApiResponse(code = 200, message = "{msg : Success Clean Basket}")
    @RequestMapping(method=RequestMethod.DELETE)
	public JSONObject cleanBasket(HttpServletRequest request) throws AbstractCustomException{
		String userID = getUserID(request.getHeader("Cookie"));
		return basketService.cleanBasket(userID);
	}
	
	
	@ApiOperation(value="장바구니 상품 수량 증가")
	@ApiImplicitParam(name = "productID", value = "상품 ID", dataType = "int", paramType = "path")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success Inc Quantity"),
            @ApiResponse(code = 609, message = "Can't Over MaxQuantity")
    })
	@RequestMapping(value="/{productID}/plus", method=RequestMethod.PUT)
	public JSONObject incQuantity(HttpServletRequest request, @PathVariable("productID")int productID) throws AbstractCustomException{
		String userID = getUserID(request.getHeader("Cookie"));
		return basketService.incQuantity(userID, productID);
	}
	
	
	@ApiOperation(value="장바구니 상품 수량 감소")
	@ApiImplicitParam(name = "productID", value = "상품 ID", dataType = "int", paramType = "path")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success Dec Quantity"),
            @ApiResponse(code = 609, message = "Can't Under ZeroQuantity")
    })
	@RequestMapping(value="/{productID}/minus", method=RequestMethod.PUT)
	public JSONObject decQuantity(HttpServletRequest request, @PathVariable("productID") int productID) throws AbstractCustomException{
		String userID = getUserID(request.getHeader("Cookie"));
		return basketService.decQuantity(userID, productID);
	}
	
	
	
	/* Cookie 값으로부터 userID 추출  */
	private String getUserID(String rawCookie) throws NullCustomException {
		String session = sessionManger.getSession(rawCookie);
		String userID = sessionManger.getUserId(session);
		logger.info(userID);
		return userID;
	}	
}
