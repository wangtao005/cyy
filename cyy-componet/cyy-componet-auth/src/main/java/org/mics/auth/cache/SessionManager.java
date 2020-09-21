package org.mics.auth.cache;

import org.mics.auth.model.AuthUser;

/**
 * 自定义session管理
 * @author mics
 * @date 2020年6月15日
 * @version  1.0
 */
public interface SessionManager {
    /**
     * 通过token创建会话
     * @param token
     * @param loginUser
     */
    void createSession(String token, AuthUser loginUser);

    /**
     * 通过token获取会话
     * @param token
     * @return
     */
    AuthUser getSession(String token);

    /**
     * 删除
     * @param token
     */
    void removeSession(String token);

    /**
     * 判断是否存在会话
     * @param token
     * @return
     */
    boolean haveSession(String token);
}
