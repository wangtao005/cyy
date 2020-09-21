package org.mics.enduser.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.mics.jpa.entity.BaseEntity;

/**
 * 登陆日志
 * @author mics
 * @date 2020年6月11日
 * @version  1.0
 */
@Entity
@Table(name = "t_end_user_login_log")
public class EndUserLoginLogDO extends BaseEntity {

    /**
     * 终端用户id
     */
    @Column(name = "end_user_id", nullable = false, columnDefinition = "varchar(64) comment '终端用户id' ")
    private String endUserId;

    /**
     * 登录的ip
     */
    @Column(name = "login_ip", nullable = false, columnDefinition = "varchar(255) comment '登录的ip' ")
    private String loginIp;

	public String getEndUserId() {
		return endUserId;
	}

	public void setEndUserId(String endUserId) {
		this.endUserId = endUserId;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
    
    
}
