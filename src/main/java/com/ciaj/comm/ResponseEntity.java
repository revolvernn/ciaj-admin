package com.ciaj.comm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Author: Ciaj.
 * @Date: 2018/6/4 09:20
 * @Description: 接口响应封装
 */
@ApiModel(value = "Response")
public class ResponseEntity<DATA> implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty("响应编码")
	private int code = 0;
	@ApiModelProperty("响应消息")
	private String msg = "success";
	@ApiModelProperty("响应数据")
	private DATA data;

	public ResponseEntity() {
	}

	public ResponseEntity(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public DATA getData() {
		return data;
	}

	public void setData(DATA data) {
		this.data = data;
	}

	public ResponseEntity(String msg) {
		this.msg = msg;
	}

	public static ResponseEntity error() {
		return error(500, "未知异常，请联系管理员");
	}

	public static ResponseEntity error(String msg) {
		return error(500, msg);
	}

	public static ResponseEntity error(int code, String msg) {
		return new ResponseEntity(code, msg);
	}

	public static ResponseEntity success(String msg) {
		return success(0, msg);
	}

	public static ResponseEntity success(int code, String msg) {
		return new ResponseEntity(code, msg);
	}

	public static ResponseEntity success() {
		return new ResponseEntity();
	}

	public ResponseEntity put(DATA value) {
		this.data = value;
		return this;
	}
}
