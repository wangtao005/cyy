package org.mics.auth.context;

import org.mics.auth.model.AuthUser;

/**
 * 登陆上下文
 * @author mics
 * @date 2020年6月15日
 * @version  1.0
 */
public interface LoginContext {

    /**
     * 获取当前登录用户
     *
     * @return
     */
    AuthUser getUser();

    /**
     * 是否登录
     *
     * @return
     */
    boolean hasLogin();

    /**
     * 验证当前用户是否拥有指定权限
     *
     * @param permission 权限名
     * @return 拥有权限：true，否则false
     */
    boolean hasPermission(String permission);
    
    

}
