package com.dongx.blog.utils;

import com.dongx.blog.security.JwtUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * UserUtils
 *
 * @author: dongx
 * Description:
 * Created in: 2018-06-25 21:34
 * Modified by:
 */
public class UserUtils {
	
	public static JwtUser getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		JwtUser user = (JwtUser) authentication.getDetails();
		return user;
	}
}
