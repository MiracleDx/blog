package com.dongx.blog.utils;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * JwtTokenUtilsTest
 *
 * @author: dongx
 * Description:
 * Created in: 2018-07-21 22:23
 * Modified by:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtTokenUtilsTest {
	
	@Resource
	private JwtTokenUtils jwtTokenUtils;

	@Test
	public void getExpirationDateFromToken() {
		String token = "yangxl eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1MzI0NDM0NDUsInN1YiI6ImFkbWluIiwianRpIjoiMTIzMjEzIiwicm9sZXMiOlsiSE9NRSIsIkFETUlOIiwiVVNFUiIsIkJMT0ciLCJUT1RBTCJdfQ.8MvkDUX98OOlYF7J6RKEP0S_n1ZhBXFTHspvLbp2EUpeD6VH4ZYqN4aPmYo5b6BL4XugBHO5yas9wU2CllJloQ";
		long expiration = jwtTokenUtils.getExpirationMillisFromToken(token);
		System.out.println(expiration);
	}
}