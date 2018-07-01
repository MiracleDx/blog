package com.dongx.blog.common;

import lombok.Getter;

/**
 * TotalStatusEnum
 *
 * @author: dongx
 * Description:
 * Created in: 2018-07-01 11:39
 * Modified by:
 */
@Getter
public enum TotalStatusEnum {
	
	CONFIRM(1, "CONFIRM"),
	CANCEL(0, "CANCEL");
	

	private Integer code;

	private String message;

	TotalStatusEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
}
