package org.mics.enduser.request;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 登陆对象
 * @author mics
 * @date 2020年6月10日
 * @version  1.0
 */
@ApiModel("账号密码登录请求")
public class LoginRequest {

    @ApiModelProperty("登录账号（必填）")
    @NotBlank(message = "登录账号不能为空")
    private String account;

    @ApiModelProperty("登录密码（必填）")
    @NotBlank(message = "登录密码不能为空")
    private String password;


	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    
    
}
