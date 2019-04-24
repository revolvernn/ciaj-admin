package com.ciaj.comm.exception;

/**
 * @Author: Ciaj.
 * @Date: 2018/12/4 13:27
 * @Description: 系统运行时异常
 */
public class BsRException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String msg;
	private int code = 500;

	public BsRException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public BsRException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}

	public BsRException(String msg, int code) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}

	public BsRException(String msg, int code, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
