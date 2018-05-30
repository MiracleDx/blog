package com.dongx.blog.controller;

import com.dongx.blog.service.Demo2Service;
import com.dongx.blog.vo.Msg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


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
	private Demo2Service demo2Service;
	
	@RequestMapping("/login")
	public String login() {
		return "login";
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
}
