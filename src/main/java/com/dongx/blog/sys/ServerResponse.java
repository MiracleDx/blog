package com.dongx.blog.sys;

import com.dongx.blog.common.ResponseCode;
import lombok.Data;

/**
 * ServerReponse
 *
 * @author: dongx
 * Description: 服务响应
 * Created in: 2018-05-13 10:49
 * Modified by:
 */
@Data
public class ServerResponse<T> {
	
	private Integer code;
	
	private String message;
	
	private String url;
	
	private T data;
	
	public ServerResponse() {
		
	}
	
	private ServerResponse(Integer Code)  {
		this.code = code;
	}
	
	private ServerResponse(Integer Code, String message) {
		this.code = code;
		this.message = message;
	}
	
	private ServerResponse(Integer Code, T data) {
		this.code = code;
		this.data = data;
	}
	
	private ServerResponse(Integer Code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	public static <T> ServerResponse<T> createBySuccess() {
		return new ServerResponse<>(ResponseCode.SUCCESS.getCode());	
	}
	
	public static <T> ServerResponse<T> createBySuccess(String message) {
		return new ServerResponse<>(ResponseCode.SUCCESS.getCode(), message);
	}

	public static <T> ServerResponse<T> createBySuccess(T data) {
		return new ServerResponse<>(ResponseCode.SUCCESS.getCode(), data);
	}
	
	public static <T> ServerResponse<T> createBySuccess(String message, T data) {
		return new ServerResponse<>(ResponseCode.SUCCESS.getCode(), message, data);
	}

	public static <T> ServerResponse<T> createbyError() {
		return new ServerResponse<>(ResponseCode.ERROR.getCode());
	}

	public static <T> ServerResponse<T> createbyError(String message) {
		return new ServerResponse<>(ResponseCode.ERROR.getCode(), message);
	}

	public static <T> ServerResponse<T> createbyError(String message, T data) {
		return new ServerResponse<>(ResponseCode.ERROR.getCode(), message, data);
	}
}
