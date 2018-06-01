package com.dongx.blog.service.Impl;

import com.dongx.blog.entity.User;
import com.dongx.blog.resposity.UserRepository;
import com.dongx.blog.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * UserSerivceImplTest
 *
 * @author: dongx
 * Description:
 * Created in: 2018-06-01 14:34
 * Modified by:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserSerivceImplTest {

	@Resource
	private UserService userService;
	
	@Resource
	private UserRepository userRepository;
	
	@Test
	public void save() {
		User user = new User();
		user.setUsername("testuser");
		user.setPassword("123456");
		userService.save(user);
	}
	
	@Test
	public void delete() {
		User user = userRepository.findUserByUsername("testUser");
		userService.delete(user);
	}
}