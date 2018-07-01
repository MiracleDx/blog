package com.dongx.blog.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

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

	private Integer floor;

	private String createUser;

	private Date createTime;
	
	private String createIp;
	
	private String replyUserId;

	private Integer status;

	private String pid;
	
}
