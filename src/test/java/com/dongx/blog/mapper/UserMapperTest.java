package com.dongx.blog.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * UserMapperTest
 *
 * @author: dongx
 * Description:
 * Created in: 2018-05-27 18:27
 * Modified by:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTest {
	
	@Resource
	private UserMapper userMapper;

	@Test
	public void findUserByUsername() {
	}
	
	@Test
	public void selectByPrimaryKey () {
		userMapper.selectByPrimaryKey("123213");
	}
}