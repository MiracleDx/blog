package com.dongx.blog.utils;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * JsonUtils
 *
 * @author: dongx
 * Description:
 * Created in: 2018-07-18 21:10
 * Modified by:
 */
public class JsonUtils {
	
	public static ObjectMapper objectMapper = new ObjectMapper();
	
	public static String convertObj2String(Object object) {
		String s = null;
		try {
			s = objectMapper.writeValueAsString(object);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	public static<T> T convertString2Obj(String s, Class<T> clazz) {
		T t = null;
		try {
			t = objectMapper.readValue(s, clazz);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return t;
	}
}
