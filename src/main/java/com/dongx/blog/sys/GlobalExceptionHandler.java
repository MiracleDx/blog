package com.dongx.blog.sys;

import com.alibaba.druid.util.StringUtils;
import com.dongx.blog.common.ResponseCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * GlobalExceptionHandler
 *
 * @author: dongx
 * Description: 全局异常处理类
 * Created in: 2018-05-13 10:40
 * Modified by:
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	
	// 发生异常跳转的页面
	public static final String DEFAULT_ERROR_VIEW = "error";

	/**
	 * 异常处理
	 * @param request
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(value = Exception.class)
	public Object ErrorHandler(HttpServletRequest request, Exception e) throws Exception {
		if (isAjax(request)) {
			return jsonErrorHandler(request, e);
		} else {
			return defaultErrorHandler(request, e);
		}
	}

	/**
	 * 默认请求的异常处理
	 * @param request
	 * @param e
	 * @return
	 * @throws Exception
	 */
	public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception e) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.addObject("exception", e);
		mv.addObject("url", request.getRequestURL());
		mv.setViewName(DEFAULT_ERROR_VIEW);
		return mv;
	}


	/**
	 * ajax请求的异常处理
	 * @param request
	 * @param e
	 * @return
	 * @throws Exception
	 */
	public ServerResponse<String> jsonErrorHandler(HttpServletRequest request, Exception e) throws Exception {
		ServerResponse<String> sr = new ServerResponse<>();
		sr.setMessage(e.getMessage());
		sr.setCode(ResponseCode.ERROR.getCode());
		sr.setData("error");
		return sr;
	}


	/**
	 * 判断是否是ajax请求
	 * @param request
	 * @return
	 */
	public boolean isAjax(HttpServletRequest request) {
		boolean flag;
		if (request.getHeader("X-Requested-With") != null 
				&& StringUtils.equals("XMLHttpRequest", request.getHeader("X-Requested-With").toString())) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}
}
