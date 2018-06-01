package com.dongx.blog.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * EncoderUtil
 *
 * @author: dongx
 * Description: 密码加密解密工具类
 * Created in: 2018-06-01 10:04
 * Modified by:
 */
public class EncoderUtil {

	/**
	 * 对密码进行加密
	 * @param password 用户传入的密码
	 * @return
	 */
	public static String PasswordEncoder(String password) {
		// 对注册密码进行加密
		BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();
		// 加密（使用用户密码当作盐值进行二次加密，暂时无法解密）
		String newPassword = bcryptEncoder.encode(password);
		return newPassword;
	}

	/**
	 * 密码比较
	 * @param password 用户传入的密码
	 * @param dbPassword 数据库中存入的密码
	 * @return
	 */
	public static boolean matches(String password, String dbPassword) {
		BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();
		return bcryptEncoder.matches(password, dbPassword);
	}
}
