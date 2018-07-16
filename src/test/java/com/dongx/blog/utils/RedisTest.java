package com.dongx.blog.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * RedisTest
 *
 * @author: dongx
 * Description:
 * Created in: 2018-07-16 22:34
 * Modified by:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
	
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	
	@Test
	public void test() throws Exception {
		stringRedisTemplate.opsForValue().set("name", "dongx");
		Assert.assertEquals("dongx", stringRedisTemplate.opsForValue().get("name"));	
	}
}
