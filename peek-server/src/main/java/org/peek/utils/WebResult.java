package org.peek.utils;

import java.io.Serializable;


/** 用于Web的请求返回
 * @author heshengchao
 * @param <T>
 */
public class WebResult<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean success;
	private T result;
	private String error;

	public void setResult(T result) {
		this.success = true;
		this.result = result;
	}

	public void setError(String error) {
		this.success = false;
		this.error = error;
	}

	public static <T> WebResult<T> ok(T data) {
		WebResult<T> resp = new WebResult<>();
		resp.setResult(data);
		resp.setSuccess(true);
		return resp;
	}

	public static <T> WebResult<T> ok() {
		return ok(null);
	}

	public static <T> WebResult<T> fail(String error) {
		WebResult<T> resp = new WebResult<>();
		resp.setError(error);
		return resp;
	}

	public boolean isSuccess() {
		return this.success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getResult() {
		return this.result;
	}

	public String getError() {
		return this.error;
	}
}
