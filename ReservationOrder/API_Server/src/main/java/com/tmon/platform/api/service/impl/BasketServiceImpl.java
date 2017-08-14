package com.tmon.platform.api.service.impl;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmon.platform.api.dao.BasketDao;
import com.tmon.platform.api.dto.BasketDto;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.exception.RangeNotSatisfyException;
import com.tmon.platform.api.exception.SQLCustomException;
import com.tmon.platform.api.service.BasketService;

@Service
public class BasketServiceImpl implements BasketService {

	
	@Autowired
	BasketDao basketDao;

	/**
	 * @author 신광원
	 * @description 장바구니에 상품 넣기 - 상품재고량과 비교한 후 적재
	 */
	public JSONObject addBasket(String userID, int productID, int quantity)
			throws SQLCustomException, RangeNotSatisfyException, NullCustomException {

		Integer maxQuantity = getMaxQuantity(productID);
		if (quantity > maxQuantity || quantity <= 0) {
			throw new RangeNotSatisfyException(609, "Quantity Range Error");
		}
		try {
			basketDao.addBasket(userID, productID, quantity);
		} catch (Exception e) {
			throw new SQLCustomException(608, "Fail : Already Exist Product in BASKET or Invalid productID");
		}
		JSONObject obj = new JSONObject();
		obj.put("msg", "Success : Insert Basket");
		return obj;
	}

	
	/**
	 * @author 신광원
	 * @description 사용자의 장바구니 전체  목록 가져오기
	 */
	public List<BasketDto> basket(String userID) {
		return basketDao.basket(userID);
	}

	
	/**
	 * @author 신광원
	 * @description 장바구니 상품 삭제
	 */
	public JSONObject removeBasket(String userID, int productID) throws NullCustomException {
		int rowCount = basketDao.removeBasket(userID, productID);
		if(rowCount == 0)
			throw new NullCustomException(615, "Deleted Zero RowCount : Invalid productID");
		JSONObject obj = new JSONObject();
		obj.put("msg", "Success Remove Basket");
		return obj;
	}

	
	/**
	 * @author 신광원
	 * @description 장바구니 비우기(초기화)
	 */
	public JSONObject cleanBasket(String userID) {
		basketDao.cleanBasket(userID);
		JSONObject obj = new JSONObject();
		obj.put("msg", "Success Clean Basket");
		return obj;
	}

	
	/**
	 * @author 신광원
	 * @description 장바구니에 담긴 상품 수량 1씩 증가(UI화면에서 화살표로 장바구니 상품 수량 변경할 경우)
	 */
	public JSONObject incQuantity(String userID, int productID) throws RangeNotSatisfyException, NullCustomException {
		Integer maxQuantity = getMaxQuantity(productID);
		Integer basketQuantity = getBasketQuantity(userID, productID);

		// 장바구니 상품의 수량은 재고량을 초과할 수 없음
		if (basketQuantity >= maxQuantity) {
			throw new RangeNotSatisfyException(609, "Can't Over MaxQuantity");
		}

		basketDao.incQuantity(userID, productID);

		JSONObject obj = new JSONObject();
		obj.put("msg", "Success Inc Quantity");
		return obj;
	}

	
	/**
	 * @author 신광원
	 * @description 장바구니에 담긴 상품 수량 1씩 감소(UI화면에서 화살표로 장바구니 상품 수량 변경할 경우)
	 */
	public JSONObject decQuantity(String userID, int productID) throws RangeNotSatisfyException, NullCustomException {
		Integer quantity = getBasketQuantity(userID, productID);
		
		// 장바구니 상품의 수량 체크 - 0보다 작게 할 수 없음
		if (quantity <= 0) {
			throw new RangeNotSatisfyException(609, "Can't Under ZeroQuantity");
		}
		basketDao.decQuantity(userID, productID);

		JSONObject obj = new JSONObject();
		obj.put("msg", "Success Dec Quantity");
		return obj;
	}

	
	/**
	 * @author 신광원
	 * @description 장바구니에 담긴 상품의 수량 가져오기
	 */
	private Integer getBasketQuantity(String userID, int productID) throws NullCustomException {
		Integer basketQuantity = basketDao.getBasketQuantity(userID, productID);
		if(basketQuantity == null) {
			throw new NullCustomException(609, "Invalid productID in BASKET");
		}
		return basketQuantity;
	}

	
	/**
	 * @author 신광원
	 * @description 상품의 재고량 가져오기  (총 수량 - 판매수량)의 값을 가져옴 
	 */
	private Integer getMaxQuantity(int productID) throws NullCustomException{
		Integer maxQuantity = basketDao.getMaxQuantity(productID);
		if(maxQuantity == null) {
			throw new NullCustomException(609, "Invalid productID in PRODUCT");
		}
		return maxQuantity;
	}

}
