package com.dongx.blog.utils;

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
		String token = "yangxl eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1MzIxODM2MzQsInN1YiI6ImFkbWluIiwianRpIjoiMTIzMjEzIiwicm9sZXMiOlsiSE9NRSIsIkFETUlOIiwiVVNFUiIsIkJMT0ciLCJUT1RBTCJdfQ.vfKZFfw3scRDIUHz7MTTMJczGrvjZIu8CwAywN5Zus-rtLAwkNnnahRkmDPGNZG7TYW5aHjQefkcvxABdhKANA";
		long expiration = jwtTokenUtils.getExpirationMillisFromToken(token);
		System.out.println(expiration);
	}
}