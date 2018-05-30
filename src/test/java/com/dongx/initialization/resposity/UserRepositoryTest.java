package com.dongx.initialization.resposity;

import com.dongx.blog.entity.User;
import com.dongx.blog.resposity.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * UserRepositoryTest
 *
 * @author: dongx
 * Description:
 * Created in: 2018-05-27 14:10
 * Modified by:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {

	@Resource
	UserRepository userRespository;
	
	@Test
	public void findUserByUsername() {
		User user  = userRespository.findUserByUsername("admin");
		System.out.println(user.toString());
	}
}