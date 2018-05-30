package com.dongx.blog.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * WebSecurityConfig
 *
 * @author: dongx
 * Description: spring-security配置
 * Created in: 2018-05-18 18:29
 * Modified by:
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


	@Resource
	private MyFilterSecurityInterceptor myFilterSecurityInterceptor;
	
	@Resource
	DataSource dataSource;
	
	@Resource
	UserDetailsService userDetailsService;

	/**
	 * 密码编码
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				//匹配的url登录后可以访问
				.authorizeRequests()
					.antMatchers("/css/**").permitAll()
					.anyRequest().authenticated() 
					.and()
				// 用户登录时跳转的页面
				.formLogin()
					.loginPage("/login")
					.defaultSuccessUrl("/")
					.failureUrl("/login?error")
					.permitAll()
					.and()
				.rememberMe()
					.tokenRepository(persistentTokenRepository())
					.tokenValiditySeconds(3600)
					.userDetailsService(userDetailsService)
					.and()
				.logout()
					.permitAll()
					.and()
		;
		http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
	}

	/**
	 * 配置记住我
	 * @return
	 */
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
		//tokenRepository.setCreateTableOnStartup(true);
		return tokenRepository;
	}
}
