package org.mics.enduser.entity;

import java.time.Instant;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.mics.enduser.enums.EndUserEnum;
import org.mics.jpa.entity.BaseEntity;
import org.mics.lang.calculate.UUIDUtil;
import org.mics.lang.security.DigestUtil;
import org.mics.lang.security.EncodeUtil;


/**
 * 终端用户
 * @author mics
 * @date 2020年6月9日
 * @version  1.0
 */
@Entity
@Table(name = "t_end_user")
@Where(clause = "bln_del = 0")
public class EndUserDO extends BaseEntity {

    /**
     * 登录账号(手机号)
     */
    @Column(name = "account", unique = true, columnDefinition = "varchar(32) comment '登录账号(手机号)' ")
    private String account;

    /**
     * 手机号
     */
    @Column(name = "phone", unique = true, columnDefinition = "varchar(11) comment '手机号' ")
    private String phone;

    /**
     * 姓名
     */
    @Column(name = "name", columnDefinition = "varchar(30) comment '姓名' ")
    private String name;

    /**
     * 头像URL
     */
    @Column(name = "avatar", columnDefinition = "varchar(150) comment '头像URL' ")
    private String avatar;

    /**
     * 用户状态
     */
    @Column(name = "status", nullable = false, columnDefinition = "tinyint(2) comment '用户状态' ")
    private EndUserEnum.Status status;

    /**
     * 登录密码
     */
    @Column(name = "login_password", columnDefinition = "varchar(100) comment '登录密码' ")
    private String loginPassword;

    /**
     * 登录密码盐
     */
    @Column(name = "login_password_salt", nullable = false, columnDefinition = "varchar(100) comment '登录密码盐' ")
    private String loginPasswordSalt;

    /**
     * 上次登录时间
     */
    @Column(name = "last_login_time", columnDefinition = "datetime comment '上次登录时间' ")
    private Date lastLoginTime;

    /**
     * 设备id
     */
    @Column(columnDefinition = "varchar(255) comment '设备id'", unique = true)
    private String deviceId;


    /**
     * 版本(uuid)
     */
    @Column(name = "version", columnDefinition = "varchar(32) comment '版本(uuid)'", nullable = false)
    private String version;

    /**
     * 构造空对象
     *
     * @return
     */
    public static EndUserDO empty() {
        EndUserDO newUser = new EndUserDO();
        //设置登录时间
        newUser.setLastLoginTime(Date.from(Instant.now()));
        //用户状态
        newUser.setStatus(EndUserEnum.Status.ACTIVE);
        //版本(uuid)
        newUser.setVersion(UUIDUtil.randomUUID());
        //生成盐
        byte[] bytes = DigestUtil.generateSalt(5);
        String salt = EncodeUtil.encodeHex(bytes);
        newUser.setLoginPasswordSalt(salt);
        return newUser;
    }
    

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public EndUserEnum.Status getStatus() {
		return status;
	}

	public void setStatus(EndUserEnum.Status status) {
		this.status = status;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getLoginPasswordSalt() {
		return loginPasswordSalt;
	}

	public void setLoginPasswordSalt(String loginPasswordSalt) {
		this.loginPasswordSalt = loginPasswordSalt;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}


	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
    
}

