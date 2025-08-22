package com.ciaj.boot.component.interceptor;

import com.ciaj.comm.ResponseEntity;
import com.ciaj.comm.annotation.OperationLog;
import com.ciaj.comm.constant.LogTypeEnum;
import com.ciaj.comm.exception.BsRException;
import com.ciaj.comm.utils.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2018/6/4 11:33
 * @Description: 异常处理
 */
@RestControllerAdvice
@Log4j2
public class ExceptionHandle {

	/**
	 * 处理自定义异常
	 */
	@OperationLog(operation = "系统-异常", content = "处理自定义异常", type = LogTypeEnum.ERROR)
	@ExceptionHandler(BsRException.class)
	public ResponseEntity handleRRException(BsRException e) {
		ResponseEntity r = new ResponseEntity(e.getCode(), e.getMessage());
		log.error(e.getMessage(), e);
		return r;
	}

	@OperationLog(operation = "系统-异常", content = "数据库中已存在该记录", type = LogTypeEnum.ERROR)
	@ExceptionHandler(DuplicateKeyException.class)
	public ResponseEntity handleDuplicateKeyException(DuplicateKeyException e) {
		log.error(e.getMessage(), e);
		return ResponseEntity.error("数据库中已存在该记录");
	}

	@OperationLog(operation = "系统-异常", content = "没有权限，请联系管理员授权", type = LogTypeEnum.ERROR)
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity handleAuthorizationException(AuthorizationException e) {
		log.error(e.getMessage(), e);
		return ResponseEntity.error(HttpStatus.UNAUTHORIZED.value(), "没有权限，请联系管理员授权");
	}

	@OperationLog(operation = "系统-异常", content = "系统异常", type = LogTypeEnum.ERROR)
	@ExceptionHandler(Exception.class)
	public ResponseEntity handleException(Exception e) {
		log.error(e.getMessage(), e);
		return ResponseEntity.error();
	}

	@OperationLog(operation = "系统-异常", content = "表单验证异常", type = LogTypeEnum.ERROR)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
		log.error(e.getMessage(), e);
		List<String> msgs = new ArrayList<>();
		fieldErrors.forEach(fieldError->{
			fieldError.getDefaultMessage();
			msgs.add(fieldError.getField()+":"+fieldError.getDefaultMessage());
		});
		String msg = StringUtil.getJoinString(",",msgs);
		log.error("表单验证失败：{}",msg);
		return ResponseEntity.error(501, msg);
	}
}