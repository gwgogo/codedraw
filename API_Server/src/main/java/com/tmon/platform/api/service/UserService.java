package com.tmon.platform.api.service;

import com.tmon.platform.api.domain.ResponseItem;

public interface UserService {
	public ResponseItem login(String user_id, String user_pw);
}
