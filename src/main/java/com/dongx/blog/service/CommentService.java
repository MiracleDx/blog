package com.dongx.blog.service;

import com.dongx.blog.dto.CommentDTO;
import com.dongx.blog.sys.ServerResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * CommentService
 *
 * @author: dongx
 * Description:
 * Created in: 2018-06-04 16:16
 * Modified by:
 */
public interface CommentService {

	/**
	 * 保存评论
	 * @param commentDTO
	 * @return
	 */
	ServerResponse save(CommentDTO commentDTO, HttpServletRequest request);

	/**
	 * 通过blogId查询所有评论
	 * @param blogId
	 * @return
	 */
	ServerResponse findAllByBlogId(String blogId);


	ServerResponse deleteByCommentId(String commentId);
}
