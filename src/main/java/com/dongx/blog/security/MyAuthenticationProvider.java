package com.dongx.blog.security;

import com.dongx.blog.resposity.UserRepository;
import com.dongx.blog.utils.EncoderUtil;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * MyAuthenticationProvider
 *
 * @author: dongx
 * Description:
 * Created in: 2018-06-01 8:52
 * Modified by:
 */
@Service
public class MyAuthenticationProvider implements AuthenticationProvider {
	
	@Resource
	private UserRepository userRepository;
	
	@Resource
	private UserDetailsService userDetailsService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		String dbPassword = userRepository.findUserByUsername(username).getPassword();
		
		// 用户密码和数据库密码进行比较
		if (EncoderUtil.matches(password, dbPassword)) {
			UserDetails userDetails  = userDetailsService.loadUserByUsername(username);
			UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken
									(userDetails, authentication.getCredentials(),userDetails.getAuthorities());
			return result;
		} else {
			throw new AuthenticationServiceException("password is not equals");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
