package org.mics.log.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.mics.jpa.entity.BaseEntity;


/**
 * 操作日志
 * @author mics
 * @date 2020年6月8日
 * @version  1.0
 */
@Entity
@Table(name = "t_operation_log")
public class OperationLogDO extends BaseEntity {

    /**
     * 登录名
     */
    @Column(name = "login_name", columnDefinition = "varchar(30) comment '登录名' ")
    private String loginName;

    /**
     * 用户操作
     */
    @Column(name = "operation", columnDefinition = "varchar(30) comment '用户操作' ")
    private String operation;

    /**
     * 请求方法
     */
    @Column(name = "method", columnDefinition = "varchar(150) comment '请求方法' ")
    private String method;

    /**
     * 请求参数
     */
    @Column(name = "params", columnDefinition = "varchar(2000) comment '请求参数' ")
    private String params;

    /**
     * IP地址
     */
    @Column(name = "ip", columnDefinition = "varchar(30) comment 'IP地址' ")
    private String ip;

    /**
     * 执行时长(毫秒)
     */
    @Column(name = "time", columnDefinition = "bigint(11) comment '执行时长(毫秒)' ")
    private Long time;

    /**
     * 具体消息
     */
    @Column(name = "message", columnDefinition = "varchar(500) comment '具体消息' ")
    private String message;
    
    

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
