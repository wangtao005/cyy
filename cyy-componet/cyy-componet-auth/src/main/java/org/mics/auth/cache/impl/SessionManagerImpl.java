package org.mics.auth.cache.impl;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.mics.auth.cache.SessionManager;
import org.mics.auth.constant.SessionContant;
import org.mics.auth.model.AuthUser;
import org.springframework.stereotype.Component;

/**
 * 会话实现类：测试时采用程序内存，生产环境劲量用Redis等内存管理工具，不然会出现程序启动用户必须重新登录等情况
 * @author mics
 * @date 2020年6月15日
 * @version  1.0
 */
@Component
public class SessionManagerImpl implements SessionManager {

    private Map<String, AuthUser> caches = new ConcurrentHashMap<>();

    @Override
    public void createSession(String token, AuthUser loginUser) {
        caches.put(SessionContant.SESSION_PREFIX + token, loginUser);
    }

    @Override
    public AuthUser getSession(String token) {
        return caches.get(SessionContant.SESSION_PREFIX + token);
    }

    @Override
    public void removeSession(String token) {
        caches.remove(SessionContant.SESSION_PREFIX + token);
    }

    @Override
    public boolean haveSession(String token) {
        return caches.containsKey(SessionContant.SESSION_PREFIX + token);
    }

}
