package com.dongx.blog.service.impl;

import com.dongx.blog.common.CommonStatus;
import com.dongx.blog.common.RoleName;
import com.dongx.blog.dto.UserDTO;
import com.dongx.blog.dto.UserInfoDTO;
import com.dongx.blog.dto.UserPasswordDTO;
import com.dongx.blog.entity.Role;
import com.dongx.blog.entity.User;
import com.dongx.blog.entity.UserInfo;
import com.dongx.blog.mapper.UserMapper;
import com.dongx.blog.resposity.RoleRepostiory;
import com.dongx.blog.resposity.UserInfoRepository;
import com.dongx.blog.resposity.UserRepository;
import com.dongx.blog.security.JwtUser;
import com.dongx.blog.service.UserService;
import com.dongx.blog.sys.ServerResponse;
import com.dongx.blog.utils.*;
import com.dongx.blog.vo.UserNicknameAndMobileVo;
import com.dongx.blog.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * UserSerivceImpl
 *
 * @author: dongx
 * Description: 用户接口实现类
 * Created in: 2018-05-26 19:35
 * Modified by:
 */
@Slf4j
@Service
public class UserSerivceImpl implements UserService {
	
	@Resource
	private UserRepository userRepository;
	
	@Resource
	private UserMapper userMapper;
	
	@Resource
	private RoleRepostiory roleRepostiory;
	
	@Resource
	private UserInfoRepository userInfoRepository;
	
	@Resource
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtTokenUtils jwtTokenUtils;

	@Value("${ftp.defaultAvatar}")
	private String defaultAvatar;

	@Value("${ftp.url}")
	private String ftpUrl;
	
	@Override
	@Transactional
	public ServerResponse save(UserDTO userDTO, HttpServletRequest request) {
		
		if (userDTO == null) {
			return ServerResponse.createByError("注册失败， 请联系管理员");
		}
		
		String username = userDTO.getUsername();
		
		// 查询注册用户名是否已经存在
		User oldUser = userRepository.findUserByUsername(username);
		if (oldUser != null) {
			log.info("注册失败， 用户名已存在：{}", username);
			return ServerResponse.createByError("该用户名已存在");
		} 
		
		// 对密码进行加密处理
		String password = userDTO.getPassword();
		String newPassword = EncoderUtils.PasswordEncoder(password);
		
		// 获取主键
		String userId = KeyGeneratorUtils.getInstance().generatorKey();
		
		User user = new User();
		// 存入用户
		user.setId(userId);
		user.setPassword(newPassword);
		user.setStatus(CommonStatus.ACTIVE.getCode());
		User userResult = userRepository.save(user);
		
		// 存入用户角色
		Role role = roleRepostiory.findByRoleName(RoleName.ROLE_USER);
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("roleId", role.getId());
		int roleResult = userMapper.insertRoleWithUser(map);
		
		// 存入用户角色信息
		UserInfo userInfo = new UserInfo();
		Date now = Date.from(Instant.now());
		String ip = IpUtils.getIpAddr(request);
		userInfo.setUserId(userId);
		userInfo.setLoginIp(ip);
		userInfo.setLoginTime(now);
		userInfo.setRegisterTime(now);
		userInfo.setRegisterIp(ip);
		userInfo.setRegisterTime(now);
		userInfo.setUpdateTime(now);
		UserInfo userInfoResult = userInfoRepository.save(userInfo);
		
		if (userResult == null || roleResult == 0 || userInfoResult == null) {
			ServerResponse.createByError("创建用户失败：{}", username);
		}
		
		log.info("注册成功， 创建用户：{}", username);
		return ServerResponse.createBySuccess("注册成功");
	}

	@Override
	@Transactional
	public ServerResponse update(UserInfoDTO userInfoDTO) {
		
		if (userInfoDTO == null) {
			return null;
		}
		
		JwtUser user = UserUtils.getUser();
		
		UserInfo userInfo = userInfoRepository.findUserInfoByUserId(user.getId());
		if (StringUtils.isNotEmpty(userInfoDTO.getNickname())) {
			userInfo.setNickname(userInfoDTO.getNickname());
		}
		if (StringUtils.isNotEmpty(userInfoDTO.getMobile())) {
			userInfo.setMobile(userInfoDTO.getMobile());
		}
		if (StringUtils.isNotEmpty(userInfoDTO.getOriginPath())) {
			userInfo.setAvatar(userInfoDTO.getOriginPath());
		}
		userInfo.setUpdateTime(Date.from(Instant.now()));
		UserInfo result = userInfoRepository.save(userInfo);
				
		if (result != null) {
			log.info("更新用户信息成功:{}", userInfo.toString());
			return ServerResponse.createBySuccess("更新用户信息成功", result);
		}
		
		log.info("更新用户信息失败:{}", user.getUsername());
		return ServerResponse.createByError("更新用户信息失败");
	}

	@Override
	@Transactional
	public ServerResponse delete(User user) {
		
		if (user == null) {
			return null;
		}
		
		user.setStatus(CommonStatus.UNACTIVE.getCode());
		User unActiveUser = userRepository.save(user);
		
		if (unActiveUser != null) {
			log.info("用户删除成功， 删除用户：{}", user.getUsername());
			return ServerResponse.createBySuccess("用户删除成功");
		}
		
		log.info("用户删除失败， 删除用户：{}", user.getUsername());
		return ServerResponse.createByError("用户删除失败");
	}

	@Override
	public ServerResponse login(String username, String password, HttpServletRequest request) {

		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			throw new AuthenticationServiceException("Username or Password not provided");
		}
		
		JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
		if (!EncoderUtils.matches(password, user.getPassword())) {
			log.info("Password not equals");
			return ServerResponse.createByError("密码不正确， 想一想再登录吧");
		}
		String token = jwtTokenUtils.createToken(user);
		log.info(token);

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				user, null, user.getAuthorities());
		log.info("authenticated user " + username + ", setting security context");
		SecurityContextHolder.getContext().setAuthentication(authentication);
		// 更新登录时间及ip
		Date now = Date.from(Instant.now());
		UserInfo userInfo = userInfoRepository.findUserInfoByUserId(user.getId());
		userInfo.setLoginTime(now);
		String ip = IpUtils.getIpAddr(request);
		userInfo.setLoginIp(ip);
		userInfoRepository.save(userInfo);
		return ServerResponse.createBySuccess("登录成功", token);
	}
	
	@Override
	public ServerResponse getUserInfo() {
		JwtUser user = UserUtils.getUser();
		
		UserInfo userInfo = userInfoRepository.findUserInfoByUserId(user.getId());
		UserVo userVo = new UserVo();
		BeanUtils.copyProperties(userInfo, userVo);
		userVo.setId(user.getId());
		userVo.setUsername(user.getUsername());
		if (StringUtils.isEmpty(userVo.getAvatar())) {
			userVo.setAvatar(defaultAvatar);
		} else {
			userVo.setAvatar(ftpUrl + userVo.getAvatar());
		}
		return ServerResponse.createBySuccess(userVo);
	}
	
	
	@Override
	public ServerResponse uploadAvatar(MultipartFile file) {
		JwtUser user = UserUtils.getUser();
		// 将图片压缩后转为输入流
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			Thumbnails.of(file.getInputStream())
					.size(120, 120)
					// 按比例压缩0.5倍
					//.scale(0.5)
					.outputFormat("jpg").toOutputStream(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			InputStream imageInputStream = new ByteArrayInputStream(os.toByteArray());
			String filePath = "/avatar/" + user.getId();
			String fileName = user.getId() + String.valueOf(System.currentTimeMillis()) + ".jpg";
			
			FtpUtils ftpUtils = new FtpUtils();
			boolean result = ftpUtils.uploadFile(filePath, fileName, imageInputStream);
			if (result) {
				String originPath = filePath + '/' + fileName;
				UserInfo userInfo = userInfoRepository.findUserInfoByUserId(user.getId());
				userInfo.setAvatar(originPath);
				userInfo.setUpdateTime(Date.from(Instant.now()));
				userInfoRepository.save(userInfo);
				return ServerResponse.createBySuccess("头像上传成功", ftpUrl + originPath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ServerResponse.createByError("头像上传失败"); 
	}

	@Override
	public ServerResponse changePassword(UserPasswordDTO userPasswordDTO) {
		JwtUser user = UserUtils.getUser();
		
		User realUser = userRepository.getOne(user.getId());
		if (EncoderUtils.matches(userPasswordDTO.getOldPassword(), realUser.getPassword())) {
			String newPassword = EncoderUtils.PasswordEncoder(userPasswordDTO.getNewPassword());
			if (EncoderUtils.matches(userPasswordDTO.getNewPassword(), realUser.getPassword())) {
				return ServerResponse.createByError("将要修改的密码不能与原密码一致");
			}
			realUser.setPassword(newPassword);
			User result = userRepository.save(realUser);
			if (result != null) {
				return ServerResponse.createBySuccess("修改密码成功， 请重新登录");
			}
		}
		return ServerResponse.createByError("与原密码不一致");
	}

	@Override
	public ServerResponse getNicknameAndMobile() {
		JwtUser user = UserUtils.getUser();
		
		UserInfo userInfo = userInfoRepository.findUserInfoByUserId(user.getId());
		UserNicknameAndMobileVo userNicknameAndMobileVo = new UserNicknameAndMobileVo();
		BeanUtils.copyProperties(userInfo, userNicknameAndMobileVo);
		return ServerResponse.createBySuccess(userNicknameAndMobileVo);
	}
}
