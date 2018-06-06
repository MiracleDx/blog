package com.dongx.blog.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * JwtTokenAuthentication
 *
 * @author: dongx
 * Description:
 * Created in: 2018-06-05 14:17
 * Modified by:
 */
public class JwtTokenAuthentication implements Authentication {
	
	private JwtUser jwtUser;

	private Boolean authentication = false;
	
	public JwtTokenAuthentication(JwtUser jwtUser, Boolean authentication) {
		this.jwtUser = jwtUser;
		this.authentication = authentication;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return jwtUser.getRoles().stream()
				.map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}
	
	@Override
	public Object getCredentials() {
		return "";
	}

	@Override
	public Object getDetails() {
		return jwtUser;
	}

	@Override
	public Object getPrincipal() {
		return jwtUser.getUsername();
	}

	@Override
	public boolean isAuthenticated() {
		return authentication;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.authentication = isAuthenticated;
	}

	@Override
	public String getName() {
		return jwtUser.getUsername();
	}

	@Override
	public String toString() {
		return "JwtTokenAuthentication{" +
				"jwtUser=" + jwtUser +
				'}';
	}
}
