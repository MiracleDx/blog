package com.dongx.blog.security;

import com.dongx.blog.entity.User;
import com.dongx.blog.resposity.UserRepository;
import com.dongx.blog.utils.EncoderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
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
 * Description: 提供用户UserDetails的具体验证方式
 * Created in: 2018-06-01 8:52
 * Modified by:
 */
@Slf4j
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

		User user = userRepository.findUserByUsername(username);

		if (user == null) {
			log.info("Username {} not found.", username);
			throw new BadCredentialsException("Username not found.");
		} else {
			String dbPassword = user.getPassword();
			
			// 用户密码和数据库密码进行比较
			if (EncoderUtil.matches(password, dbPassword)) {
				UserDetails userDetails  = userDetailsService.loadUserByUsername(username);
				
				UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken
										(userDetails, authentication.getCredentials(),userDetails.getAuthorities());

				return result;
			} else {
				log.info("{}: Wrong password.", username);
				throw new BadCredentialsException("Wrong password.");
			}
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
