package org.mics.auth.enums;


/**
 * 用户异常状态
 * @author mics
 * @date 2020年6月15日
 * @version  1.0
 */
public enum AuthExceptionState {

    /**
     * 用户未登录
     */
    NOT_LOGIN_ERROR(1401, "用户未登录"),

    /**
     * 账号密码错误
     */
    USERNAME_PWD_ERROR(1402, "账号密码错误"),

    /**
     * 登录已过期，请重新登录
     */
    LOGIN_EXPPIRED(1403, "登录已过期，请重新登录"),

    /**
     * 账号被冻结
     */
    ACCOUNT_FREEZE_ERROR(1404, "账号被冻结"),

    /**
     * 用户没有分配角色，获取菜单失败
     */
    NO_ROLE_ERROR(1405, "用户没有分配角色，获取菜单失败"),

    /**
     * 验证码错误
     */
    VALID_CODE_ERROR(1406, "验证码错误"),

    /**
     * 没有权限访问资源
     */
    NO_PERMISSION(1500, "没有权限访问资源");

    private Integer code;

    private String message;

    AuthExceptionState(int code, String message) {
        this.code = code;
        this.message = message;
    }

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    
    
}
