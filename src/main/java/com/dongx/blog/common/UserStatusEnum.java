package com.dongx.blog.common;

import lombok.Getter;

/**
 * UserStatusEnum
 *
 * @author: dongx
 * Description: 用户状态枚举类
 * Created in: 2018-06-01 14:45
 * Modified by:
 */
@Getter
public enum UserStatusEnum {
	
	ACTIVE(1, "ACTICE"),
	UNACTVE(0, "UNACTIVE");
	
	private Integer code;
	
	private String message;
	
	UserStatusEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
}
