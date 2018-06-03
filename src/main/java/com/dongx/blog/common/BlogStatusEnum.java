package com.dongx.blog.common;

import lombok.Getter;

/**
 * BlogStatusEnum
 *
 * @author: dongx
 * Description:
 * Created in: 2018-06-03 17:48
 * Modified by:
 */
@Getter
public enum BlogStatusEnum {
	
	ACTICE(0001, "ACTIVE"),
	UNACTIVE(0002, "UNACTIVE"),
	// 草稿
	DRAFT(0003, "DRAFT");
	
	private Integer code;
	
	private String message;
	
	BlogStatusEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
}
