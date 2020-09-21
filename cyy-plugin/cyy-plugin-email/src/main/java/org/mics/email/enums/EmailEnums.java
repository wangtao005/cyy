package org.mics.email.enums;

/**
 * 邮箱验证码类型
 * @author mics
 * @date 2020年5月19日
 * @version  1.0
 */
public class EmailEnums {
    /**
     * 验证码发送类型(0:登录;1:注册;2:找回密码;3:修改密码)
     */
    public enum CodeType {
        //登录
        LOG_IN,
        //注册
        REGISTERED,
        //找回密码
        RETRIEVE_PASSWORD,
        //修改密码
        MODIFY_PASSWORD
    }

    /**
     * 验证码状态(0:未校验;1:已校验)
     */
    public enum CodeStatus {
        //未校验
        NOT_VERIFIED,
        //已校验
        VERIFIED
    }

    /**
     * 发送状态(0:失败;1:成功)
     */
    public enum SendStatus {
        //失败
        FAILURE,
        //成功
        SUCCESS
    }
}
