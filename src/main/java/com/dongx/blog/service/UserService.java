package com.dongx.blog.service;

import com.dongx.blog.entity.User;
import com.dongx.blog.sys.ServerResponse;

/**
 * UserService
 *
 * @author: dongx
 * Description: 用户接口
 * Created in: 2018-05-26 19:35
 * Modified by:
 */
public interface UserService {

	/**
	 * 注册用户
	 * @param user
	 * @return
	 */
	ServerResponse save(User user);

	/**
	 * 更新用户
	 * @param user
	 * @return
	 */
	ServerResponse update(User user);

	/**
	 * 删除用户（假删除）
	 * @param user
	 * @return
	 */
	ServerResponse delete(User user);
}
