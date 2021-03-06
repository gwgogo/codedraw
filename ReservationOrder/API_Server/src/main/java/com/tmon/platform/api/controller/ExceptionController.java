package com.tmon.platform.api.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.tmon.platform.api.exception.AuthException;
import com.tmon.platform.api.exception.DateFormatException;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.exception.PreConditionException;
import com.tmon.platform.api.exception.RangeNotSatisfyException;
import com.tmon.platform.api.exception.SQLCustomException;
import com.tmon.platform.api.exception.StopServiceException;

@ControllerAdvice
@RestController
public class ExceptionController {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

	/* 로그인 로직 */
	@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED) // 412(사전조건 실패)
	@ExceptionHandler(PreConditionException.class)
	public Map<String, String> UserExceptionHandler(PreConditionException e) {
		logger.error("Error Code : " + e.getErrorStatus().get("errCode") + ", Error Message : " + e.getErrorStatus().get("errMsg"));
		return e.getErrorStatus();
	}

	/* 로그인 유무 체크, 어드민 권한 체크시 사용 - 인터셉터에서 사용 */
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED) // 401(권한 없음)
	@ExceptionHandler(AuthException.class)
	public Map<String, String> AuthExceptionHandler(AuthException e) {
		logger.error("Error Code : " + e.getErrorStatus().get("errCode") + ", Error Message : " + e.getErrorStatus().get("errMsg"));
		return e.getErrorStatus();
	}

	/* Parameter 요청 잘못됐을 시 사용 */
	@ResponseStatus(value = HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE) // 416(처리할 수 없는 요청범위)
	@ExceptionHandler(RangeNotSatisfyException.class)
	public Map<String, String> RangeNotSatisfyExceptionHandler(RangeNotSatisfyException e) {
		logger.error("Error Code : " + e.getErrorStatus().get("errCode") + ", Error Message : " + e.getErrorStatus().get("errMsg"));
		return e.getErrorStatus();
	}

	/* SQL 에러시 사용 */
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR) // 500(내부 서버 오류)
	@ExceptionHandler(SQLCustomException.class)
	public Map<String, String> SQLCustomException(SQLCustomException e) {
		logger.error("Error Code : " + e.getErrorStatus().get("errCode") + ", Error Message : " + e.getErrorStatus().get("errMsg"));
		return e.getErrorStatus();
	}

	/* NullPointer 예외시 사용 */
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR) // 500(내부 서버 오류)
	@ExceptionHandler(NullCustomException.class)
	public Map<String, String> NullCustomException(NullCustomException e) {
		logger.error("Error Code : " + e.getErrorStatus().get("errCode") + ", Error Message : " + e.getErrorStatus().get("errMsg"));
		return e.getErrorStatus();
	}

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR) // 500(내부 서버 오류)
	@ExceptionHandler(DateFormatException.class)
	public Map<String, String> DateFormatException(DateFormatException e) {
		logger.error("Error Code : " + e.getErrorStatus().get("errCode") + ", Error Message : " + e.getErrorStatus().get("errMsg"));
		return e.getErrorStatus();
	}

	/* UI, API 데이터 통신 조건 불일치시 사용 */
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR) // 500(내부 서버 오류)
	@ExceptionHandler(StopServiceException.class)
	public Map<String, String> StopServiceExcpetion(StopServiceException e) {
		logger.error("Error Code : " + e.getErrorStatus().get("errCode") + ", Error Message : " + e.getErrorStatus().get("errMsg"));
		return e.getErrorStatus();
	}

	/* RequestParameter 타입 불일치 예외시 사용 */
	@ResponseStatus(value = HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE) // 416(처리할 수 없는 요청범위)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public Map<String, String> MethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
		NullCustomException exception = new NullCustomException(623, "RequestParameterType Mismatch");
		logger.error("Error Code : " + exception.getErrorStatus().get("errCode") + ", Error Message : " + exception.getErrorStatus().get("errMsg"));
		return exception.getErrorStatus();
	}
}
