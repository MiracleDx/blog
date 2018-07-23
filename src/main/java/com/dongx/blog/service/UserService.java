package com.dongx.blog.service;

import com.dongx.blog.dto.UserDTO;
import com.dongx.blog.dto.UserInfoDTO;
import com.dongx.blog.dto.UserPasswordDTO;
import com.dongx.blog.entity.User;
import com.dongx.blog.sys.ServerResponse;
import org.springframework.web.multipart.MultipartFile;

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
	ServerResponse save(UserDTO user, HttpServletRequest request);

	/**
	 * 更新用户
	 * @param userInfoDTO
	 * @return
	 */
	ServerResponse update(UserInfoDTO userInfoDTO);

	/**
	 * 删除用户（假删除）
	 * @param user
	 * @return
	 */
	ServerResponse delete(User user);

	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return
	 */
	ServerResponse login(String username, String password, HttpServletRequest request);

	/**
	 * 用户注销
	 * @return
	 */
	ServerResponse logout(HttpServletRequest request);

	/**
	 * 查询用户信息
	 * @param token
	 * @return
	 */
	ServerResponse getUserInfo();

	/**
	 * 上传头像信息
	 * @param file
	 * @return
	 */
	ServerResponse uploadAvatar(MultipartFile file);

	/**
	 * 修改用户密码
	 * @param userPasswordDTO
	 * @return
	 */
	ServerResponse changePassword(UserPasswordDTO userPasswordDTO, HttpServletRequest request);

	/**
	 * 获取用户昵称和手机号
	 * @return
	 */
	ServerResponse getNicknameAndMobile();
}
