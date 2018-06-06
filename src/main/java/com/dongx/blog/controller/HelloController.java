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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Objects;


/**
 * HelloController
 * 
 * @author dongx
 * Description:
 * Created in: 2018-05-13 9:40
 * Modified by:
 */
@Controller
@Slf4j
public class HelloController {
	
	@Resource
	private UserDetailsService userDetailsService;
	
	@Resource
	private UserRepository userRepository;

	@Resource
	private UserService userService;

	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	

	@RequestMapping("/register")
	public String register() {
		return "register";
	}

	@RequestMapping("/")
	public String index(Model model){
		Msg msg =  new Msg("测试标题","测试内容","欢迎来到HOME页面,您拥有 ROLE_HOME 权限");
		model.addAttribute("msg", msg);
		return "home";
	}
	
	@RequestMapping("/admin")
	@ResponseBody
	public String hello(){
		return "hello admin";
	}


	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	@ResponseBody
	public JwtUser auth() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (Objects.isNull(authentication)) {
			return null;
		}
		return (JwtUser) authentication.getDetails();
	}

	@RequestMapping(value = "/authlogin", method = RequestMethod.POST)
	@ResponseBody
	public ServerResponse createAuthenticationToken(
			@RequestBody User user) throws AuthenticationException {
		return ServerResponse.createBySuccess(userService.login(user.getUsername(), user.getPassword()));
	}
}
