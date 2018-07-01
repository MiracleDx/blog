package com.dongx.blog.service;

import com.dongx.blog.entity.TotalCount;

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
	void addStatus(String userId, String blogId);

	/**
	 * 取消点赞
	 * @param userId
	 * @param blogId
	 * @return
	 */
	void cancelStatus(String userId, String blogId);

	/**
	 * 增加点赞总数
	 * @param blogId
	 * @return
	 */
	void addLikeCount(String blogId);

	/**
	 * 减少点赞总数
	 * @param blogId
	 * @return
	 */
	void decLikeCount(String blogId);


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
