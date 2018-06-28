package com.dongx.blog.vo;

import com.dongx.blog.dto.CommentDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * CommentVo
 *
 * @author: dongx
 * Description:
 * Created in: 2018-06-28 21:55
 * Modified by:
 */
@Data
public class CommentVo {

	private String id;

	private String blogId;

	private String content;

	private String createUser;

	private Date createTime;

	private Integer status;

	private String pid;

	private String username;

	private String nickname;

	private String avatar;

	private List<CommentDTO> children;
}
