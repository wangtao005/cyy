package org.mics.log.bean;

/**
 * 操作日志
 * @author mics
 * @date 2020年5月22日
 * @version  1.0
 */
public class OperationLogBean {

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 用户操作
     */
    private String operation;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求参数
     */
    private String params;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 执行时长(毫秒)
     */
    private Long time;

    /**
     * 具体消息
     */
    private String message;
    
    
    

	public OperationLogBean(String loginName, String operation, String method, String params, String ip, Long time,
			String message) {
		super();
		this.loginName = loginName;
		this.operation = operation;
		this.method = method;
		this.params = params;
		this.ip = ip;
		this.time = time;
		this.message = message;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    
    
}
