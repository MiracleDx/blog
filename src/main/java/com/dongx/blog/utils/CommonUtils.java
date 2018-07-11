package com.dongx.blog.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * CommonUtils
 *
 * @author: dongx
 * Description:
 * Created in: 2018-07-11 21:08
 * Modified by:
 */
public class CommonUtils {

	/**
	 * 通过字段属性去重
	 * @param keyExtrator
	 * @param <T>
	 * @return
	 */
	public static <T> Predicate<T> distinctBy(Function<? super T, Object> keyExtrator) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtrator.apply(t), Boolean.TRUE) == null;
	}
}
