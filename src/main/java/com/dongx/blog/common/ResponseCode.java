package com.dongx.blog.common;

import lombok.Getter;

/**
 * ResponseCode
 *
 * @author: dongx
 * Description: 响应码
 * Created in: 2018-05-21 14:10
 * Modified by:
 */
@Getter
public enum ResponseCode {
	
	SUCCESS(0,"SUCCESS"),
	ERROR(1,"ERROR"),
	NEED_LOGIN(10,"NEED_LOGIN"),
	ILLEGAL_ARGUMENT(2,"ILLEGAL_ARGUMENT");
	
	private final Integer code;
	
	private final String desc;
	
	ResponseCode(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
}
