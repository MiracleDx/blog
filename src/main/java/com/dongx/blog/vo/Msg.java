package com.dongx.blog.vo;

import lombok.Data;

/**
 * Msg
 *
 * @author: dongx
 * Description:
 * Created in: 2018-05-27 20:13
 * Modified by:
 */
@Data
public class Msg {
	
	private String title;
	
	private String content;
	
	private String etraInfo;

	public Msg(String title, String content, String etraInfo) {
		super();
		this.title = title;
		this.content = content;
		this.etraInfo = etraInfo;
	}
}
