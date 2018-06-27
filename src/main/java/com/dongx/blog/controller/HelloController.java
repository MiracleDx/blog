package com.dongx.blog.controller;

import com.dongx.blog.entity.User;
import com.dongx.blog.resposity.UserRepository;
import com.dongx.blog.security.JwtUser;
import com.dongx.blog.service.UserService;
import com.dongx.blog.sys.ServerResponse;
import com.dongx.blog.vo.Msg;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;


/**
 * HelloController
 * 
 * @author dongx
 * Description:
 * Created in: 2018-05-13 9:40
 * Modified by:
 */
@CrossOrigin
@RestController
@Slf4j
@RequestMapping
public class HelloController {
	
	@Resource
	private UserDetailsService userDetailsService;
	
	@Resource
	private UserRepository userRepository;

	@Resource
	private UserService userService;

	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public JwtUser auth() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (Objects.isNull(authentication)) {
			return null;
		}	
		return (JwtUser) authentication.getDetails();
	}

	@PostMapping("/authlogin")
	public ServerResponse createAuthenticationToken(
			@RequestBody User user, HttpServletRequest request) throws AuthenticationException {
		return userService.login(user.getUsername(), user.getPassword(), request);
	}

	@PostMapping("/register")
	public ServerResponse register(@RequestBody User user, HttpServletRequest request) {
		return userService.save(user, request);
	}
}
