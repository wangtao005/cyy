package org.mics.email.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.mics.email.enums.EmailEnums;
import org.mics.jpa.entity.BaseEntity;


@Entity
@Table(name="t_email_code")
public class EmailCodeDO extends BaseEntity{

	 /**
     * 手机号
     */
    @Column(name = "email", columnDefinition = "varchar(32) comment '邮箱账号'")
    private String email;

    /**
     * 发送类型(0:登录;1:注册;2:找回密码;3:修改密码)
     */
    @Column(name = "type", columnDefinition = "int(1) comment '发送类型(0:登录;1:注册;2:找回密码;3:修改密码)'")
    private EmailEnums.CodeType type;

    /**
     * 验证码
     */
    @Column(name = "code", columnDefinition = "varchar(20) comment '验证码'")
    private String code;

    /**
     * 验证码状态(0:未校验;1:已校验)
     */
    @Column(name = "status", columnDefinition = "int(1) comment '验证码状态(0:未校验;1:已校验)'")
    private EmailEnums.CodeStatus status;


    /**
     * 接收消息
     */
    @Column(name = "message", columnDefinition = "varchar(100) comment '接收消息'")
    private String message;


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public EmailEnums.CodeType getType() {
		return type;
	}


	public void setType(EmailEnums.CodeType type) {
		this.type = type;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public EmailEnums.CodeStatus getStatus() {
		return status;
	}


	public void setStatus(EmailEnums.CodeStatus status) {
		this.status = status;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	

    
}
