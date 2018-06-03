package com.dongx.blog.service;

import com.dongx.blog.dto.UserInfoDTO;
import com.dongx.blog.entity.User;
import com.dongx.blog.sys.ServerResponse;

import javax.servlet.http.HttpServletRequest;

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
	ServerResponse save(User user, HttpServletRequest request);

	/**
	 * 更新用户
	 * @param user
	 * @return
	 */
	ServerResponse update(User user, UserInfoDTO userInfoDTO);

	/**
	 * 删除用户（假删除）
	 * @param user
	 * @return
	 */
	ServerResponse delete(User user);
}
