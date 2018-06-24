package com.dongx.blog.dto;

import lombok.Data;

import java.util.Date;

/**
 * CommentDTO
 *
 * @author: dongx
 * Description:
 * Created in: 2018-06-07 9:00
 * Modified by:
 */
@Data
public class CommentDTO {

	private String id;

	private String blogId;

	private String content;

	private String createUser;

	private Date crreateTime;

	private Integer status;
}
