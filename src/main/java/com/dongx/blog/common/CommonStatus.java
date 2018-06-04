package com.dongx.blog.common;

import lombok.Getter;

/**
 * CommonStatus
 *
 * @author: dongx
 * Description: 通用状态枚举类
 * Created in: 2018-06-04 8:52
 * Modified by:
 */
@Getter
public enum CommonStatus {

	ACTIVE(1, "ACTICE"),
	UNACTIVE(0, "UNACTIVE");

	private Integer code;

	private String message;

	CommonStatus(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
}
