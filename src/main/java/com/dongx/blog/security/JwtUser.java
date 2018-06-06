package com.dongx.blog.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JwtUser
 *
 * @author: dongx
 * Description:
 * Created in: 2018-06-05 11:04
 * Modified by:
 */
@Data
public class JwtUser implements UserDetails, Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	
	private String username;
	
	private String password;
	
	private List<String> roles;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	private boolean accountNonExpired = true;
	private boolean accountNonLocked = true;
	private boolean credentialsNonExpired = true;
	private boolean enabled = true;

	public JwtUser() {
	}


	public JwtUser(String id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
		this(id, username, password, true, true, true, true, authorities);
	}


	public JwtUser(String id, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
		if (username != null && !"".equals(username) && password != null) {
			this.id = id;
			this.username = username;
			this.password = password;
			this.enabled = enabled;
			this.accountNonExpired = accountNonExpired;
			this.credentialsNonExpired = credentialsNonExpired;
			this.accountNonLocked = accountNonLocked;
			this.roles = authorities.stream().distinct().map(e -> e.getAuthority()).collect(Collectors.toList());
			this.authorities = authorities;
		} else {
			throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
		}
	}

	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@JsonIgnore
	public String getId() {
		return id;
	}
	
	@JsonIgnore
	@Override
	public String getPassword() {
		return password;
	}
	
	@Override
	public String getUsername() {
		return username;
	}

	/**
	 * 是否过期
	 * @return
	 */
	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	/**
	 * 是否未锁定
	 * @return
	 */
	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	/**
	 * 密码是否过期
	 * @return
	 */
	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	/**
	 * 是否激活
	 * @return
	 */
	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return enabled;
	}
}
