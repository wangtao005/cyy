package org.mics.auth.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.mics.auth.context.LoginContextHolder;
import org.mics.auth.model.AuthUser;
import org.mics.auth.service.AuthService;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *  auth 接口实现
 * @author mics
 * @date 2020年6月15日
 * @version  1.0
 */
@Service
@DependsOn("springContextHolder")
public class AuthServiceImpl implements AuthService {
    /**
     * 分隔符
     */
    private static final String URL_SEPARATOR = "/";

    /**
     * 固定长度
     */
    private static final Integer URL_LENGTH = 3;

    /**
     * 检查当前登录用户是否拥有当前请求的的权限
     *
     * @return
     */
    @Override
    public boolean checkAuth() {
        //获取上下文用户
        HttpServletRequest request = getRequest();
        AuthUser user = LoginContextHolder.getContext().getUser();
        if (null == user) {
            return false;
        }

        //判断地址是否具有权限
        String requestURI = request.getRequestURI();
        String[] str = requestURI.split(URL_SEPARATOR);

        //超过三个长度可能是get/id restful 风格地址
        if (str.length > URL_LENGTH) {
            requestURI = URL_SEPARATOR + str[1] + URL_SEPARATOR + str[2] + URL_SEPARATOR + str[3];
        }

        //校验
        if (LoginContextHolder.getContext().hasPermission(requestURI)) {
            return true;
        }
        return false;
    }

    /**
     * 检查当前登录用户是否拥有指定的角色访问当
     *
     * @param roleNames 角色名称集合
     */
    @Override
    public boolean checkAuthByRoles(String[] roleNames) {
        return false;
    }

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes == null ? null : requestAttributes.getRequest();
    }
}
