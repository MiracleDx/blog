package com.dongx.blog.security;

import com.dongx.blog.common.UserStatusEnum;
import com.dongx.blog.entity.Permission;
import com.dongx.blog.entity.User;
import com.dongx.blog.mapper.PermissionMapper;
import com.dongx.blog.mapper.UserMapper;
import com.dongx.blog.resposity.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * CustomerUserService
 *
 * @author: dongx
 * Description: 提供了获取UserDetails的方式
 * Created in: 2018-05-26 19:40
 * Modified by:
 */
@Slf4j
@Component
public class MyUserDetailsService implements UserDetailsService {
	
	@Resource
	private UserMapper userMapper;
	
	@Resource
	private UserRepository userRepository;
	
	@Resource
	private PermissionMapper permissionMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 通过用户名查询用户
		User user = userRepository.findUserByUsername(username);
		
		// 查询用户的url权限
		List<Permission> permissionList = permissionMapper.findByUserid(user.getId());
		
		if (permissionList == null && permissionList.size() == 0) {
			throw new SecurityException("do not have a permission");
		}

		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		for (Permission permission : permissionList) {
			if (permission != null && !StringUtils.isEmpty(permission.getName())) {
				GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getName());
				// 将权限信息添加到GrantedAuthority中， 进行全权限验证会用到
				grantedAuthorities.add(grantedAuthority);
			}
		}
		
		// 判断用户的四个状态
		boolean isEnabled = this.isEnabled(user.getStatus());
		boolean isAccountNonExpired = this.isAccountNonExpired();
		boolean isAccountNonLocked = this.isAccountNonLocked();
		boolean isCredentialsNonExpired = this.isCredentialsNonExpired();

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				isEnabled, isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired, grantedAuthorities);
	}

	/**
	 * 是否激活
	 * @return
	 */
	public boolean isEnabled(Integer status) {
		return status.equals(UserStatusEnum.ACTIVE.getCode());
	}

	/**
	 * 是否过期
	 * @return
	 */
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * 是否锁定
	 * @return
	 */
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * 否过期
	 * @return
	 */
	public boolean isCredentialsNonExpired() {
		return true;
	}
}
