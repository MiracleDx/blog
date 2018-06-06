package com.dongx.blog.security;

import com.dongx.blog.utils.JwtTokenUtil;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * JwtAuthenticationTokenFilter
 *
 * @author: dongx
 * Description:
 * Created in: 2018-06-05 11:20
 * Modified by:
 */
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	@Resource
	private UserDetailsService userDetailsService;
	
	@Value("${jwt.header}")
	private String tokenHeader = "Authorization";

	@Resource
	private JwtTokenUtil jwtTokenUtil;

	public JwtAuthenticationTokenFilter(JwtTokenUtil jwtTokenUtil) {
		this.jwtTokenUtil = jwtTokenUtil;
	}
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			Optional<Authentication> authentication = jwtTokenUtil.verifyToken(request);
			log.debug("VerifyTokenFilter result: {}",authentication.orElse(null));
			SecurityContextHolder.getContext().setAuthentication(authentication.orElse(null));
			filterChain.doFilter(request,response);
		} catch (JwtException e) {
			e.printStackTrace();
			SecurityContextHolder.clearContext();
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			//可以在这里指定重定向还是返回错误接口示例
		}
	}
}
