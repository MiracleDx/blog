package com.dongx.blog.utils;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * KeyGeneratorUtils
 *
 * @author: dongx
 * Description: 随机主键生成器工具类
 * Created in: 2018-05-21 20:19
 * Modified by:
 */
public class KeyGeneratorUtils {

	private static KeyGeneratorUtils instance;
	
	public static KeyGeneratorUtils getInstance() {
		if (instance == null) {
			synchronized (KeyGeneratorUtils.class) {
				if (instance == null) {
					instance = new KeyGeneratorUtils();
				}
			}
		}
		return instance;
	}
	
	/**
	 * 生成32位随机数
	 * @return
	 */
	public String generatorKey() {
		// 随机数生成器
		SecureRandom random = new SecureRandom();
		
		// 当前时间戳
		Long current = System.currentTimeMillis() + random.nextInt(999999999);
		String currentResult = current.toString();
		
		// 取uuid的前8位
		String uuid = UUID.randomUUID().toString();
		String uuidResult = uuid.substring(0, uuid.indexOf("-"));
		
		// 取随机数
		String randomResult = String.valueOf(random.nextInt(Math.abs(Integer.parseInt(currentResult.substring(0, 10)) + 1000)));
		
		// 随机一个字母
		char[] chars = {'y', 'a', 'n', 'g', 'x', 'i', 'a', 'o', 'l', 'i', 'n', 'w', 'o', 'x', 'i', 'h', 'u', 'a', 'n', 'n', 'i', 'a'};
		char charResult = chars[random.nextInt(chars.length)];
		
		// 拼接随机数
		StringBuilder sb = new StringBuilder();
		sb.append(currentResult).append(uuidResult).append(randomResult);
		
		// 随机主键
		String result = "";
		
		// 拼接随机数的长度
		int temp = sb.length();
		// 最终生成随机数的长度
		int resultTemp = 32;
		// 差值
		int num = resultTemp - temp;
		
		// 使随机数为32位
		if (num == 0) {
			result = sb.toString();
		} else {
			for (int i = 0; i < num -1; i++) {
				sb.append(String.valueOf(random.nextInt(5)));
			}
			sb.append(String.valueOf(charResult));
			result = sb.toString();
		}
		
		// 保证生成的随机数为32为
		if(result.length() != 32) {
			result = generatorKey();
		}
		return result;
	}

	/**
	 * 生成带有前缀的随机数
	 * @param str
	 * @return
	 */
	public String generatorKey(String str) {
		int length = str.length();
		String result = generatorKey();
		result = result.substring(length, result.length());
		StringBuilder sb = new StringBuilder(str);
		sb.append(result);
		return sb.toString();
	}
}
