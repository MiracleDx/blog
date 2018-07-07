package com.dongx.blog.controller;

import com.dongx.blog.dto.UserInfoDTO;
import com.dongx.blog.dto.UserPasswordDTO;
import com.dongx.blog.entity.UserInfo;
import com.dongx.blog.service.UserService;
import com.dongx.blog.sys.ServerResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

	@PostMapping("/avatarUpload")
	public ServerResponse avatarUpload(MultipartFile file) {
		return userService.uploadAvatar(file);
	}
	
	@PutMapping("/update")
	public ServerResponse udpate(@RequestBody UserInfoDTO userInfoDTO) {
		return userService.update(userInfoDTO);
	}
	
	@PutMapping("/changePassword")
	public ServerResponse changePassword(@RequestBody UserPasswordDTO userPasswordDTO) {
		return userService.changePassword(userPasswordDTO);
	}
}
