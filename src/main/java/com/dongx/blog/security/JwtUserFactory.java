package com.dongx.blog.security;

import com.dongx.blog.dto.UserRoleDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

/**
 * JwtUserFactory
 *
 * @author: dongx
 * Description:
 * Created in: 2018-06-05 11:08
 * Modified by:
 */
public class JwtUserFactory {

	private JwtUserFactory() {
	}

	public static JwtUser create(UserRoleDTO UserRoleDTO) {
		return new JwtUser(
				UserRoleDTO.getId(),
				UserRoleDTO.getUsername(),
				UserRoleDTO.getPassword(),
				mapToGrantedAuthorities(UserRoleDTO.getRoles()
						.stream()
						.map(e -> e.getRoleName())
						.collect(Collectors.toList()))
		);
	}

	private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
		return authorities.stream()
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}
}
