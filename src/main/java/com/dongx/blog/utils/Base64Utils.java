package com.dongx.blog.utils;

import org.apache.commons.codec.binary.Base64;

/**
 * Base64Utils
 *
 * @author: dongx
 * Description:
 * Created in: 2018-07-24 21:38
 * Modified by:
 */
public class Base64Utils {

	public String decryptBASE64(String key) {
		byte[] bt;
		bt = (new Base64()).decode(key);
		//如果出现乱码可以改成： String(bt, "utf-8")或 gbk
		return new String(bt);
	}
}
