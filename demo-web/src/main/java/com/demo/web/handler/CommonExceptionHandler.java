package com.demo.web.handler;

import java.sql.SQLException;

import com.demo.api.dto.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.demo.common.exception.BusinessException;

@ControllerAdvice
@ResponseBody
public class CommonExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(CommonExceptionHandler.class);

	@ExceptionHandler(SQLException.class)
	public Response handleSqlExp(SQLException e) {
		log.error("数据库异常 ", e);
		return new Response().failure("service is too busy");
	}
	
	@ExceptionHandler(BusinessException.class)
	public Response handleBizExp(BusinessException e) {
		log.error("业务异常 ", e);
		return new Response().failure("service is too busy");
	}

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public Response handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		log.error("参数解析失败", e);
		return new Response().failure("could_not_read_json");
	}

	/**
	 * 405 - Method Not Allowed
	 */
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Response handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		log.error("不支持当前请求方法", e);
		return new Response().failure("request_method_not_supported");
	}

	/**
	 * 415 - Unsupported Media Type
	 */
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public Response handleHttpMediaTypeNotSupportedException(Exception e) {
		log.error("不支持当前媒体类型", e);
		return new Response().failure("content_type_not_supported");
	}

	/**
	 * 500 - Internal Server Error
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public Response handleException(Exception e) {
		log.error("服务运行异常", e);
		return new Response().failure(e.getMessage());
	}
}
