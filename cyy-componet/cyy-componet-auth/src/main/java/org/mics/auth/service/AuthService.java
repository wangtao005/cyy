package org.mics.auth.service;

/**
 * auth 相关接口
 * @author mics
 * @date 2020年6月15日
 * @version  1.0
 */
public interface AuthService {


    /**
     * 检查当前登录用户是否拥有当前请求的的权限
     *
     * @return
     */
    boolean checkAuth();

    /**
     * 检查当前登录用户是否拥有指定的角色访问当
     *
     * @param roleNames 角色名称集合
     */
    boolean checkAuthByRoles(String[] roleNames);
}
