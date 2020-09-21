package org.mics.core.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mics.lang.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 平台端权限拦截器
 * @author zls
 * 2020年7月8日
 */
@Component
@Order(2)
public class PlatformInterceptor extends HandlerInterceptorAdapter {
	private final String[] CONST_KEY = {"platformLogin","/admin/login.html","登录已失效，请重新登录",".html","请求失败，请稍后再试","/admin/index.html"};
	private static final Logger LOGGER = LoggerFactory.getLogger(PlatformInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
    	
    	Object obj = request.getSession().getAttribute(CONST_KEY[0]);
    	if(obj==null) {
    		try {
    			if(request.getRequestURI().endsWith(CONST_KEY[3])&&!request.getRequestURI().endsWith(CONST_KEY[1])) {
    				response.sendRedirect(CONST_KEY[1]);
    			}else if(request.getRequestURI().endsWith(CONST_KEY[1])){
    				return true;
    			}else {
    				throw new CustomException(CONST_KEY[2]);	
    			}
			} catch (IOException e) {
				LOGGER.error("拦截器获取uri出错",e);
				throw new CustomException(CONST_KEY[4]);
			}
    		return false;
    	}else if(request.getRequestURI().endsWith(CONST_KEY[1])){
    		try {
				response.sendRedirect(CONST_KEY[5]);
			} catch (IOException e) {
				LOGGER.error("拦截器获取uri出错",e);
				throw new CustomException(CONST_KEY[4]);
			}
    		return false;
    	}
    	
    	return true;
       
    }
}
