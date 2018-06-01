package com.dongx.blog.controller;

import com.dongx.blog.entity.User;
import com.dongx.blog.service.UserService;
import com.dongx.blog.sys.ServerResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * UserController
 *
 * @author: dongx
 * Description:
 * Created in: 2018-05-31 17:19
 * Modified by:
 */
@RestController
public class UserController {
	
	@Resource
	private UserService userService;
	
	
	@PostMapping("/register")
	public ServerResponse register(User user) {
		return userService.save(user);		
	}
}
