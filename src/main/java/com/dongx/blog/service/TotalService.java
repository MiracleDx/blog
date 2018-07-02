package com.dongx.blog.service;

import com.dongx.blog.entity.TotalCount;
import com.dongx.blog.sys.ServerResponse;

/**
 * TotalService
 *
 * @author: dongx
 * Description:
 * Created in: 2018-07-01 11:07
 * Modified by:
 */
public interface TotalService {

	/**
	 * 点赞
	 * @param userId
	 * @param blogId
	 * @return
	 */
	ServerResponse addStatus(String blogId);

	/**
	 * 取消点赞
	 * @param userId
	 * @param blogId
	 * @return
	 */
	ServerResponse cancelStatus(String blogId);
	
	/**
	 * 增加评论数
	 * @param blogId
	 * @return
	 */
	void addReplyCount(String blogId);

	/**
	 * 查询点赞和回复数量
	 * @param blogId
	 * @return
	 */
	TotalCount findLikeAndReplyCount(String blogId);
}
