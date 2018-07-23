package com.dongx.blog.config;

import com.dongx.blog.security.JwtAuthenticationTokenFilter;
import com.sun.net.httpserver.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * WebConfig
 *
 * @author: dongx
 * Description: filter实现注入bean
 * Created in: 2018-07-21 23:48
 * Modified by:
 */
//@Configuration
//public class WebConfig {
//
//	@Bean
//	public Filter JwtAuthenticationTokenFilter() {
//		return new JwtAuthenticationTokenFilter();
//	}
//
//
//	@Bean
//	public FilterRegistrationBean jwtAuthenticationTokenFilterRegistration() {
//		FilterRegistrationBean registration = new FilterRegistrationBean();
//		registration.setFilter(new DelegatingFilterProxy("JwtAuthenticationTokenFilter"));
//		--registration.addUrlPatterns("/uploadDatas");
//		registration.setName("JwtAuthenticationTokenFilter");
//		registration.setOrder(1);
//		return registration;
//	}
//}
