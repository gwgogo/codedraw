package com.tmon.platform.api.dao;

import java.util.List;

import com.tmon.platform.api.dto.BasketDto;

public interface BasketDao {
	public void addBasket(BasketDto basketDto);
	public List<BasketDto> basket(String user_id);
}
