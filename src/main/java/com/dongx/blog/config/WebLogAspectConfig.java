package com.dongx.blog.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * WebLogAspect
 *
 * @author: dongx
 * Description: AOP切面配置
 * Created in: 2018-05-18 19:10
 * Modified by:
 */
@Aspect
@Component
@Slf4j
public class WebLogAspectConfig {

	ThreadLocal<Long> startTime = new ThreadLocal<>();

	@Pointcut("execution(public * com.dongx.blog.controller..*.*(..))")
	public void webLog() {}
	
	@Before("webLog()")
	public void doBefor(JoinPoint joinPoint) throws Throwable {
		startTime.set(System.currentTimeMillis());
		
		// 接收到请求，记录请求内容
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		// 记录下请求内容
		log.info("服务器接收到请求");
		log.info("请求<->URL : " + request.getRequestURL().toString());
		log.info("请求<->HTTP_METHOD : " + request.getMethod());
		log.info("请求<->IP : " + request.getRemoteAddr());
		log.info("请求<->CLASS_METHOD : " +  joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
		log.info("请求<->ARGS : " + Arrays.toString(joinPoint.getArgs()));
	}

	@AfterReturning(returning = "ret", pointcut = "webLog()")
	public void doAfterReturning(Object ret) throws Throwable {
		// 处理完请求，返回内容
		log.info("服务器开始响应");
		log.info("服务器响应<->RESPONSE : " + ret);
		log.info("服务器响应<->SPEND TIME : {} 毫秒", (System.currentTimeMillis() - startTime.get()));
	}
}
