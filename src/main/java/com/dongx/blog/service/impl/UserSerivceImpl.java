package com.dongx.blog.service.impl;

import com.dongx.blog.common.CommonStatus;
import com.dongx.blog.common.RoleName;
import com.dongx.blog.dto.UserInfoDTO;
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
import com.dongx.blog.utils.EncoderUtil;
import com.dongx.blog.utils.GeneratorKeyUtil;
import com.dongx.blog.utils.IpUtil;
import com.dongx.blog.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
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
	private JwtTokenUtil jwtTokenUtil;
	

/*	@Resource
	private AuthenticationManager authenticationManager;*/

	@Override
	@Transactional
	public ServerResponse save(User user, HttpServletRequest request) {
		
		if (user == null) {
			return null;
		}
		
		String username = user.getUsername();
		
		// 查询注册用户名是否已经存在
		User oldUser = userRepository.findUserByUsername(username);
		if (oldUser != null) {
			log.info("注册失败， 用户名已存在：{}", username);
			return ServerResponse.createByError("该用户名已存在");
		} 
		
		// 对密码进行加密处理
		String password = user.getPassword();
		String newPassword = EncoderUtil.PasswordEncoder(password);
		
		// 获取主键
		String userId = GeneratorKeyUtil.getInstance().generatorKey();
		
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
		Role roleResult = userMapper.insertRoleWithUser(map);
		
		// 存入用户角色信息
		UserInfo userInfo = new UserInfo();
		Date now = Date.from(Instant.now());
		String ip = IpUtil.getIpAddr(request);
		userInfo.setUserId(userId);
		userInfo.setLoginIp(ip);
		userInfo.setLoginTime(now);
		userInfo.setRegisterTime(now);
		userInfo.setRegisterIp(ip);
		userInfo.setRegisterTime(now);
		userInfo.setUpdateTime(now);
		UserInfo userInfoResult = userInfoRepository.save(userInfo);
		
		if (userResult == null || roleResult == null || userInfoResult == null) {
			ServerResponse.createByError("创建用户失败：{}", username);
		}
		
		log.info("注册成功， 创建用户：{}", username);
		return ServerResponse.createBySuccess("注册成功");
	}

	@Override
	@Transactional
	public ServerResponse update(User user, UserInfoDTO userInfoDTO) {
		
		if (user == null) {
			return null;
		}
		
		UserInfo userInfo = new UserInfo();
		BeanUtils.copyProperties(userInfoDTO, userInfo);
		userInfo.setUserId(user.getId());
		
		UserInfo result = userInfoRepository.save(userInfo);
				
		if (result != null) {
			log.info("更新用户信息成功:{}", userInfo.toString());
			ServerResponse.createBySuccess(result);
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
	public ServerResponse login(String username, String password) {


		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			throw new AuthenticationServiceException("Username or Password not provided");
		}
		
		JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
		String token = jwtTokenUtil.createToken(user);
		log.info(token);

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				user, null, user.getAuthorities());
		log.info("authenticated user " + username + ", setting security context");
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return ServerResponse.createBySuccess(token);
	}
}
