package com.tmon.platform.api.service;

import java.sql.SQLException;
import java.util.List;

import org.json.simple.JSONObject;

import com.tmon.platform.api.dto.BasketDto;

public interface BasketService {
	public JSONObject addBasket(BasketDto basketDto) throws SQLException;
	public List<BasketDto> basket(String user_id);
}
