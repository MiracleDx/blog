package com.dongx.blog.security;

import com.dongx.blog.dto.UserRoleDTO;
import com.dongx.blog.entity.Permission;
import com.dongx.blog.mapper.PermissionMapper;
import com.dongx.blog.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * JwtUserDetailsService
 *
 * @author: dongx
 * Description:
 * Created in: 2018-06-05 11:10
 * Modified by:
 */
@Slf4j
@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Resource
	private UserMapper userMapper;
	
	@Resource
	private PermissionMapper permissionMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserRoleDTO user = userMapper.findByUsername(username);

		if (user == null) {
			log.info("No user found with username {}.", username);
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		} else {

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
			
			UserDetails userDetails = new JwtUser(user.getId(), username, user.getPassword(),
					true, true, true, true, grantedAuthorities);

			return userDetails;
		}
	}
}
