package org.mics.enduser.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("用户信息VO")
public class EndUserTokenVO implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1413060925259547553L;

	/**
     * 登录账号(手机号)
     */
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
    
    

}
