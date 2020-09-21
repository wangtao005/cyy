package org.mics.auth.security;

import java.util.Set;

import org.mics.auth.context.LoginContext;
import org.mics.auth.enums.AuthExceptionState;
import org.mics.auth.exception.AuthException;
import org.mics.auth.model.AuthUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 用户登录上下文
 * @author mics
 * @date 2020年6月15日
 * @version  1.0
 */
@Component
public class LoginContextSpringSecurityImpl implements LoginContext {
    /**
     * 获取当前登录用户
     *
     * @return
     */
    @Override
    public AuthUser getUser() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
            throw new AuthException(AuthExceptionState.NOT_LOGIN_ERROR.getCode(), AuthExceptionState.NOT_LOGIN_ERROR.getMessage());
        } else {
            return (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
    }

    /**
     * 是否登录
     *
     * @return
     */
    @Override
    public boolean hasLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        } else {
            if (authentication instanceof AnonymousAuthenticationToken) {
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * 验证当前用户是否拥有指定权限
     *
     * @param permission 权限名
     * @return 拥有权限：true，否则false
     */
    @Override
    public boolean hasPermission(String permission) {
        Set<String> permissions =  getUser().getPermissions();

        if(permissions ==null){
            return false;
        }

        return permissions.contains(permission);
    }
}
