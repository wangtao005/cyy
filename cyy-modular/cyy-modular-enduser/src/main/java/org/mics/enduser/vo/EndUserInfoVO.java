package org.mics.enduser.vo;

import java.io.Serializable;
import java.util.Date;

import org.mics.enduser.enums.EndUserEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("用户信息")
public class EndUserInfoVO  implements Serializable{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1643880137091001889L;
	
	@ApiModelProperty("用户ID")
    private String id;
	
	@ApiModelProperty("登录账号(手机号)")
    private String account;

    /**
     * 手机号
     */
	@ApiModelProperty("手机号")
    private String phone;

    /**
     * 姓名
     */
	@ApiModelProperty("姓名")
    private String name;

    /**
     * 头像URL
     */
	@ApiModelProperty("头像URL")
    private String avatar;

    /**
     * 用户状态
     */
	@ApiModelProperty("用户状态")
    private EndUserEnum.Status status;
    
    /**
     * 上次登录时间
     */
	@ApiModelProperty("上次登录时间")
    private Date lastLoginTime;
	
	@ApiModelProperty("创建时间")
	private Date createDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
    
    
}
