package org.mics.token.utils;

import javax.servlet.http.HttpServletRequest;

import org.mics.core.context.SpringContextHolder;
import org.mics.token.constant.TokenConstant;
import org.mics.token.exception.TokenException;
import org.mics.token.model.CurrentUser;
import org.mics.token.service.TokenService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * token工具类
 * @author mics
 * @date 2020年5月22日
 * @version  1.0
 */
public class TokenUtil {
	
    /**
     * spring bean tokenService
     */
    private static TokenService tokenService = SpringContextHolder.getBean(TokenService.class);

    /**
     * spring bean HttpServletRequest
     */
    private static HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

    /**
     * 获取token中自定义字段
     *
     * @param fieldName
     * @return
     */
    public static Object getCustomField(String fieldName) {
        // 1、请求中的token
        String token = getToken();

        // 2、从token中拿到自定义字段
        return tokenService.getCustomField(token, fieldName);
    }

    /**
     * 获取请求token中的userID
     *
     * @return
     */
    public static String getUserId() {
        // 1、从header中获取token
        String token = getToken();

        return tokenService.getUserId(token);
    }

    /**
     * 获取请求token中的userID
     *
     * @return CurrentUser
     */
    public static CurrentUser getUser() {
        // 1、从header中获取token
        String token = getToken();

        return tokenService.getUser(token);
    }


    /**
     * 请求中的token
     *
     * @return token
     */
    private static String getToken() {
        // 1、从header中获取token
        String token = servletRequest.getHeader(TokenConstant.TOKEN_STR);

        // 2、如果header中不存在token，则从参数中获取token
        if (token == null || token.trim().length() == 0) {
            token = servletRequest.getParameter(TokenConstant.TOKEN_STR);
        }

        // 最终没有抛异常
        if (token == null || token.trim().length() == 0) {
            throw new TokenException("请登录后使用系统！");
        }

        return token;
    }
}
