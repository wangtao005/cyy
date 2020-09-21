package org.mics.token.service;

import org.mics.token.jwt.JwtPayLoad;
import org.mics.token.model.CurrentUser;

/**
 * token
 * @author mics
 * @date 2020年6月9日
 * @version  1.0
 */
public interface TokenService {
    /**
     * 生成token
     *
     * @param jwtPayLoad
     * @return
     */
    String generate(JwtPayLoad jwtPayLoad);

    /**
     * 校验token并返回当前用户账号
     *
     * @param token token
     * @return token
     */
    String verify(String token);

    /**
     * 通过token获取用户信息
     * 1、不能用在默认token上
     * 2、理论上不会出现为空的可能，都是进过token检验后才能调用该方法
     *
     * @param token
     * @return
     */
    CurrentUser getUser(String token);

    /**
     * 仅需要ID的时候
     *
     * @param token
     * @return
     */
    String getUserId(String token);

    /**
     * 获取自定义字段的值
     *
     * @param token
     * @param fieldName
     * @return
     */
    Object getCustomField(String token, String fieldName);

}
