package com.json;

import java.io.Serializable;

/**
 * JSON result for AJAX request.
 * 
 * @author YunxiaoXie
 *
 */
public class JSONResult implements Serializable {

	private static final long serialVersionUID = 1L;

	public JSONResult() {
	}

	public JSONResult(int _code) {
		this.code = _code;
	}

	public JSONResult(int _code, String exceptionName) {
		this.code = _code;
		this.exceptionMsg = exceptionName;
	}

	public JSONResult(int _code, String exceptionName, String exceptionStack) {
		this.code = _code;
		this.exceptionMsg = exceptionName;
		this.exceptionStackTrace = exceptionStack;
	}

	/**
	 * back code.
	 */
	private Integer code;
	/**
	 * exception name.
	 */
	private String exceptionMsg;
	/**
	 * exception stack trace.
	 */
	private String exceptionStackTrace;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getExceptionMsg() {
		return exceptionMsg;
	}

	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}

	public String getExceptionStackTrace() {
		return exceptionStackTrace;
	}

	public void setExceptionStackTrace(String exceptionStackTrace) {
		this.exceptionStackTrace = exceptionStackTrace;
	}
}
