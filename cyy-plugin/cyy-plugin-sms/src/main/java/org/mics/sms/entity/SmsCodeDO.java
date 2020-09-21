package org.mics.sms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.mics.jpa.entity.BaseEntity;
import org.mics.sms.enums.SmsEnums;

@Entity
@Table(name="t_sms_code")
public class SmsCodeDO extends BaseEntity{

	  /**
     * 手机号
     */
    @Column(name = "phone", columnDefinition = "varchar(11) comment '手机号'")
    private String phone;

    /**
     * 发送类型(0:登录;1:注册;2:找回密码;3:修改密码)
     */
    @Column(name = "type", columnDefinition = "int(1) comment '发送类型(0:登录;1:注册;2:找回密码;3:修改密码)'")
    private SmsEnums.CodeType type;

    /**
     * 验证码
     */
    @Column(name = "code", columnDefinition = "varchar(20) comment '验证码'")
    private String code;

    /**
     * 验证码状态(0:未校验;1:已校验)
     */
    @Column(name = "status", columnDefinition = "int(1) comment '验证码状态(0:未校验;1:已校验)'")
    private SmsEnums.CodeStatus status;


    /**
     * 接收消息
     */
    @Column(name = "message", columnDefinition = "varchar(100) comment '接收消息'")
    private String message;


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public SmsEnums.CodeType getType() {
		return type;
	}


	public void setType(SmsEnums.CodeType type) {
		this.type = type;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public SmsEnums.CodeStatus getStatus() {
		return status;
	}


	public void setStatus(SmsEnums.CodeStatus status) {
		this.status = status;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}

    
}
