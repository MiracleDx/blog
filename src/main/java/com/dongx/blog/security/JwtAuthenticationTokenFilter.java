package com.dongx.blog.security;

import com.dongx.blog.common.TokenConstant;
import com.dongx.blog.utils.JwtTokenUtils;
import com.dongx.blog.utils.RedisUtils;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
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
@WebFilter
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	@Resource
	private UserDetailsService userDetailsService;
	
	@Resource
	private JwtTokenUtils jwtTokenUtils;
	
	@Resource
	private RedisUtils redisUtils;

	@Value("${jwt.header}")
	private String AUTH_HEADER_NAME;
	
	@Value("${jwt.expiration}")
	private long expiration;

	public JwtAuthenticationTokenFilter(JwtTokenUtils jwtTokenUtils, RedisUtils redisUtils, String authHeaderName, UserDetailsService userDetailsService, long expiration) {
		this.jwtTokenUtils = jwtTokenUtils;
		this.redisUtils = redisUtils;
		this.AUTH_HEADER_NAME = authHeaderName;
		this.userDetailsService = userDetailsService;
		this.expiration = expiration;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,OPTIONS,DELETE");
			response.setHeader("Access-Control-Allow-Headers", "Origin,X-Requested-With,Content-Type,Accept,Authorization,token");
			// 获取请求方法 如果是预请求直接返回
			String method = request.getMethod();
			if (!method.equalsIgnoreCase("options")) {
				Optional<Authentication> authentication = jwtTokenUtils.verifyToken(request);
				log.debug("VerifyTokenFilter result: {}", authentication.orElse(null));
				if (authentication.isPresent()) {
					String token = request.getHeader(AUTH_HEADER_NAME);
					//判断该token是否处于注销黑名单中
					String logoutKey = TokenConstant.LOGOUT_TOKEN + token;
					if (redisUtils.exists(logoutKey)) {
						log.debug("VerifyToken failed, redis has this logoutToken: {}", redisUtils.get(logoutKey) + ":" + logoutKey);
						response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
						return;
					}
					//判断该token是否刚刚发生重置密码等操作
					String updateKey = TokenConstant.UPDATE_TOKEN + token;
					if (redisUtils.exists(updateKey)) {
						log.debug("VerifyToken failed, redis has this updateToken: {}", redisUtils.get(updateKey) + ":" + updateKey);
						response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
						return;
					}
					// 判断是否重新下发token
					long expirationMillisFromToken = jwtTokenUtils.getExpirationMillisFromToken(token);
					// 如果token的剩余有效期小于过期时间的一半则下发新的阀值
					if (expiration / 2 > expirationMillisFromToken) {
						JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(((JwtUser) authentication.get().getDetails()).getUsername());
						String refreshToken = jwtTokenUtils.createToken(user);
						response.setHeader("Access-Control-Expose-Headers", TokenConstant.REFRESH_TOKEN);
						response.setHeader(TokenConstant.REFRESH_TOKEN, refreshToken);
						log.info("refreshToken success: {}", user.getId() + ":" + refreshToken);
					}
				}
				SecurityContextHolder.getContext().setAuthentication(authentication.orElse(null));
				filterChain.doFilter(request,response);
			} else {
				return;
			}
		} catch (JwtException e) {
			e.printStackTrace();
			SecurityContextHolder.clearContext();
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			//可以在这里指定重定向还是返回错误接口示例
		}
	}
}
