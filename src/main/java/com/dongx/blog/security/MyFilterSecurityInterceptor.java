package com.dongx.blog.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * MyFilterSecurityInterceptor
 *
 * @author: dongx
 * Description: 拦截器对用户url请求进行拦截
 * Created in: 2018-05-27 20:01
 * Modified by:
 */
@Slf4j
@Service
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

	@Value("${jwt.header}")
	private String tokenHeader;

	@Value("${jwt.tokenHead}")
	private String tokenHead;
	
	@Resource
	private FilterInvocationSecurityMetadataSource securityMetadataSource;

	@Resource
	public void setMyAccessDecisionManager(MyAccessDecisionManager myAccessDecisionManager) {
		super.setAccessDecisionManager(myAccessDecisionManager);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		FilterInvocation filterInvocation = new FilterInvocation(request, response, chain);
		invoke(filterInvocation);
	}

	/**
	 *
	 * @param filterInvocation 里面有一个被拦截的url
	 */
	public void invoke(FilterInvocation filterInvocation) throws ServletException, IOException {

		//调用MyInvocationSecurityMetadataSource的getAttributes(Object object)这个方法获取filterInvocation对应的所有权限
		//再调用MyAccessDecisionManager的decide方法来校验用户的权限是否足够

		InterceptorStatusToken token = super.beforeInvocation(filterInvocation);
		try {
			//执行下一个拦截器
			filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
		} finally {
			super.afterInvocation(token, null);
		}
	}


	@Override
	public void destroy() {

	}

	@Override
	public Class<?> getSecureObjectClass() {
		return FilterInvocation.class;
	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return this.securityMetadataSource;
	}
}