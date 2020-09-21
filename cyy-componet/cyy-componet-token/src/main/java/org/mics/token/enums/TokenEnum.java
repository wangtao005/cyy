package org.mics.token.enums;

/**
 * token
 * @author mics
 * @date 2020年5月21日
 * @version  1.0
 */
public class TokenEnum {

    /**
     * token请求的来源
     */
    public enum TokenSource {
        //微信小程序用户
        WX,
        //admin后台
        ADMIN,
        //终端用户
        END_USER
    }

    /**
     * 用户类型
     */
    public enum UserType {
        // 超级管理员
        SUPER_ADMIN,
        // 总渠道
        TOTAL_CHANNEL,
        // 超级总代理
        SUPER_GENERAL_AGENT,
        // 总代理
        GENERAL_AGENT,
        // 代理
        AGENT
    }
}
