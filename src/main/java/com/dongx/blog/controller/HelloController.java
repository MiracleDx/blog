package com.dongx.blog.controller;

import com.dongx.blog.dto.UserDTO;
import com.dongx.blog.entity.User;
import com.dongx.blog.resposity.UserRepository;
import com.dongx.blog.security.JwtUser;
import com.dongx.blog.service.BlogService;
import com.dongx.blog.service.UserService;
import com.dongx.blog.sys.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
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
	private UserService userService;
	
	@Resource
	private BlogService blogService;

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
	public ServerResponse register(@RequestBody UserDTO userDTO, HttpServletRequest request) {
		return userService.save(userDTO, request);
	}
	
	@GetMapping("/authlogout")
	public ServerResponse logout(HttpServletRequest request) {
		return userService.logout(request);
	}

	@GetMapping("/findAllBlog")
	public ServerResponse findAllBlog() {
		return blogService.findAll();
	}
}
