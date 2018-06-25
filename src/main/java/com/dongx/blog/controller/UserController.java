package com.dongx.blog.controller;

import com.dongx.blog.service.UserService;
import com.dongx.blog.sys.ServerResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Resource
	private UserService userService;

	@GetMapping("/getUserInfo")
	public ServerResponse getUserInfo() {
		return userService.getUserInfo();
	}
}
