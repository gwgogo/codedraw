package com.tmon.platform.api.service.impl;

import java.util.List;

import javax.xml.bind.ParseConversionEvent;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger logger = LoggerFactory.getLogger(BasketServiceImpl.class);
	@Autowired
	BasketDao basketDao;

	public JSONObject addBasket(String user_id, int product_id, int quantity)
			throws SQLCustomException, RangeNotSatisfyException, NullCustomException {

		Integer maxQuantity = getMaxQuantity(product_id);
		if (quantity > maxQuantity.intValue() || quantity <= 0) {
			throw new RangeNotSatisfyException(609, "Quantity Range Error");
		}
		try {
			basketDao.addBasket(user_id, product_id, quantity);
		} catch (Exception e) {
			throw new SQLCustomException(608, "Fail : Already Exist Product in BASKET or Invalid ProductID");
		}
		JSONObject obj = new JSONObject();
		obj.put("msg", "Success : Insert Basket");
		return obj;
	}

	public List<BasketDto> basket(String user_id) {
		return basketDao.basket(user_id);
	}

	

	public JSONObject removeBasket(String user_id, int product_id) throws NullCustomException {
		int rowCount = basketDao.removeBasket(user_id, product_id);
		if(rowCount == 0)
			throw new NullCustomException(615, "Deleted Zero RowCount : Invalid ProductID");
		JSONObject obj = new JSONObject();
		obj.put("msg", "Success Remove Basket");
		return obj;
	}

	
	public JSONObject cleanBasket(String user_id) {
		basketDao.cleanBasket(user_id);
		JSONObject obj = new JSONObject();
		obj.put("msg", "Success Clean Basket");
		return obj;
	}

	
	public JSONObject incQuantity(String user_id, int product_id) throws RangeNotSatisfyException, NullCustomException {
		int maxQuantity = getMaxQuantity(product_id);
		Integer basketQuantity = getBasketQuantity(user_id, product_id);

		// 장바구니 상품의 수량은 재고량을 초과할 수 없음
		if (basketQuantity >= maxQuantity) {
			throw new RangeNotSatisfyException(609, "Can't Over MaxQuantity");
		}

		basketDao.incQuantity(user_id, product_id);

		JSONObject obj = new JSONObject();
		obj.put("msg", "Success Inc Quantity");
		return obj;
	}

	
	public JSONObject decQuantity(String user_id, int product_id) throws RangeNotSatisfyException, NullCustomException {
		Integer quantity = getBasketQuantity(user_id, product_id);
		
		// 장바구니 상품의 수량 체크 - 0보다 작게 할 수 없음
		if (quantity <= 0) {
			throw new RangeNotSatisfyException(609, "Can't Under ZeroQuantity");
		}
		basketDao.decQuantity(user_id, product_id);

		JSONObject obj = new JSONObject();
		obj.put("msg", "Success Dec Quantity");
		return obj;
	}

	/* 테스트코드에서 사용하기위해 잠시 public - 배포할때는 private로 변경 */
	public int getBasketQuantity(String user_id, int product_id) throws NullCustomException {
		int basketQuantity;
		try {
			basketQuantity = basketDao.getBasketQuantity(user_id, product_id);
		}catch(NullPointerException e) {
			throw new NullCustomException(609, "Invalid Product_id in BASKET");
		}
		return basketQuantity;
	}

	
	/* 테스트코드에서 사용하기위해 잠시 public - 배포할때는 private로 변경 */
	public Integer getMaxQuantity(int product_id) throws NullCustomException{
		/* Integer 타입으로 선언하면 null로 비교 가능 - getBasketQuantity()와 비교 */ 
		Integer maxQuantity = basketDao.getMaxQuantity(product_id);
		if(maxQuantity == null) {
			throw new NullCustomException(609, "Invalid Product_id in PRODUCT");
		}
		/*try {
			maxQuantity = basketDao.getMaxQuantity(product_id);
		}catch(NullPointerException e) {
			throw new NullCustomException(609, "Invalid Product_id in PRODUCT");
		}*/
		return maxQuantity;
	}

}
