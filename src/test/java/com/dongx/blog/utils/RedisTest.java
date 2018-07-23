package com.dongx.blog.utils;

import com.dongx.blog.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.Serializable;

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
	
	@Resource
	private RedisTemplate redisTemplate;
	
	@Resource
	private RedisUtils redisUtils;
	
	@Test
	public void test() throws Exception {
		stringRedisTemplate.opsForValue().set("name", "dongx");
		Assert.assertEquals("dongx", stringRedisTemplate.opsForValue().get("name"));	
	}
	
	@Test
	public void test2() {
		ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
		operations.set("dongx", "name");
		Assert.assertEquals("dongx", stringRedisTemplate.opsForValue().get("name"));
	}

	@Test
	public void test3() {
		redisUtils.set("dongx", "name");
	}
	
	@Test
	public void test4() {
		User user = new User();
		user.setUsername("dongx");
		redisUtils.set("User", user);
	}

	@Test
	public void test5() {
		System.out.println(redisUtils.get("User"));
		User user = (User) redisUtils.get("User");
		System.out.println(user.toString());
	}

	@Test
	public void test6() {
		User user = new User();
		user.setId("3");
		user.setUsername("dongx");
		redisUtils.hmSet("User", user.getId(), user);
	}
}
