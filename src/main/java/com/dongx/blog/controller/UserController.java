package com.dongx.blog.controller;

import com.dongx.blog.entity.User;
import com.dongx.blog.service.UserService;
import com.dongx.blog.sys.ServerResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * UserController
 *
 * @author: dongx
 * Description:
 * Created in: 2018-05-31 17:19
 * Modified by:
 */
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Resource
	private UserService userService;

	@RequestMapping("/register")
	public String register() {
		return "register";
	}
	
	
	@PostMapping("/login")
	public ServerResponse register(User user, HttpServletRequest request) {
		return userService.save(user, request);		
	}
}
