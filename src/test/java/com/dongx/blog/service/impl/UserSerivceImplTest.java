package com.dongx.blog.service.impl;

import com.dongx.blog.dto.UserDTO;
import com.dongx.blog.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * UserSerivceImplTest
 *
 * @author: dongx
 * Description:
 * Created in: 2018-06-23 16:08
 * Modified by:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserSerivceImplTest {

	@Resource
	private UserService userService;
	
	@Test
	public void save() {
		UserDTO user = new UserDTO();
		user.setUsername("ajsdlkfjadklsf");
		user.setPassword("2132324324");

		HttpServletRequest request = null;
		userService.save(user, request);
	}
}