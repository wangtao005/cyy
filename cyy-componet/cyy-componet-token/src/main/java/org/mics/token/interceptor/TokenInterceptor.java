package org.mics.token.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mics.token.annotation.IgnoreToken;
import org.mics.token.constant.TokenConstant;
import org.mics.token.exception.TokenException;
import org.mics.token.service.TokenService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * token拦截器
 * @author mics
 * @date 2020年5月22日
 * @version  1.0
 */
@Component
@Order(2)
public class TokenInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	 IgnoreToken ignoreToken;

         //如果请求不是后台方法不需要验证token
         if (handler instanceof HandlerMethod) {
             ignoreToken = ((HandlerMethod) handler).getMethodAnnotation(IgnoreToken.class);
         } else {
             return true;
         }

         //如果有@IgnoreToken注解，则不验证token
         if (ignoreToken != null) {
             return true;
         }

         //从header中获取token
         String token = request.getHeader(TokenConstant.TOKEN_STR);

         //如果header中不存在token，则从参数中获取token
         if (token == null || token.trim().length() == 0) {
             token = request.getParameter(TokenConstant.TOKEN_STR);
         }
         
         //token为空
         if (token == null || token.trim().length() == 0) {
             throw new TokenException("登录失效，请重新登录");
         }

         //验证token并记录用户登录账号
         String account = tokenService.verify(token);
         request.setAttribute(TokenConstant.TOKEN_STR + "_Account", account);
         return true;
    }
}
