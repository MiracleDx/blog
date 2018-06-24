package com.dongx.blog.controller;

import com.dongx.blog.entity.User;
import com.dongx.blog.service.UserService;
import com.dongx.blog.sys.ServerResponse;
import org.apache.catalina.Server;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping("/getUserInfo")
	public ServerResponse getUserInfo() {
		return userService.getUserInfo();
	}
}
