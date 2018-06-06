package com.dongx.blog.utils;

import com.dongx.blog.security.JwtTokenAuthentication;
import com.dongx.blog.security.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * JwtTokenUtil
 *
 * @author: dongx
 * Description:
 * Created in: 2018-06-05 10:49
 * Modified by:
 */
@Component
public class JwtTokenUtil {
	
	@Value("${jwt.secret}")
	private String SECRET;

	@Value("${jwt.header}")
	private String AUTH_HEADER_NAME;
	
	@Value("${jwt.expiration}")
	private long VALIDITY_TIME_MS;

	@Value("${jwt.tokenHead}")
	private String tokenHead;

	/**
	 * 从用户中创建一个jwt Token
	 * @param jwtUser 用户
	 * @return token
	 */
	public String createToken(JwtUser jwtUser) {
		
		if (StringUtils.isBlank(jwtUser.getUsername())) {
			throw new IllegalArgumentException("Cannot create JWT Token without username");
		}

		if (jwtUser.getAuthorities() == null || jwtUser.getAuthorities().isEmpty()) {
			throw new IllegalArgumentException("User doesn't have any privileges");
		}
		
		return Jwts.builder()
				.setExpiration(new Date(System.currentTimeMillis() + VALIDITY_TIME_MS))
				.setSubject(jwtUser.getUsername())
				.setId(jwtUser.getId())
				.claim("roles", jwtUser.getRoles())
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
	}
	/**
	 * 从token中取出用户
	 */
	public JwtUser parseToken(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(SECRET)
				.parseClaimsJws(token.trim())
				.getBody();
		JwtUser jwtUser = new JwtUser();
		jwtUser.setId(claims.getId());
		jwtUser.setUsername(claims.getSubject());
		jwtUser.setRoles((List) claims.get("roles"));
		return jwtUser;
	}

	/**
	 * 验签
	 */
	public Optional<Authentication> verifyToken(HttpServletRequest request) {
		String authHeader = request.getHeader(this.AUTH_HEADER_NAME);
		
		if (authHeader == null) {
			return Optional.empty();
		}
		// The part after "authHeader "
		final String token = authHeader.substring(tokenHead.length());

		if (token != null && !token.isEmpty()){
			final JwtUser user = parseToken(token.trim());
			if (user != null) {
				return Optional.of(new JwtTokenAuthentication(user, true));
			}
		}
		return Optional.empty();
	}

	public Boolean validateToken(String token, JwtUser jwtUser) {
		JwtUser userFromToken = parseToken(token);
		// final Date created = getCreatedDateFromToken(token);
		// final Date expiration = getExpirationDateFromToken(token);
		return (StringUtils.equals(userFromToken.getId(),jwtUser.getId())
				&& StringUtils.equals(userFromToken.getUsername(), jwtUser.getUsername()));
	}
}
