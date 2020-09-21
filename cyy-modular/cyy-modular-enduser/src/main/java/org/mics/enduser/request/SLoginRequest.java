package org.mics.enduser.request;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 登陆对象
 * @author mics
 * @date 2020年6月10日
 * @version  1.0
 */
@ApiModel("账号+验证码登录请求")
public class SLoginRequest {

    @ApiModelProperty("账号(必填)")
    @NotBlank(message = "登录账号不能为空")
    private String account;

    @ApiModelProperty("短信校验")
    @Valid
    @NotNull(message = "校验码不能为空")
    private VerificationCodeCheckRequest verificationCodeCheckRequest;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public VerificationCodeCheckRequest getVerificationCodeCheckRequest() {
		return verificationCodeCheckRequest;
	}

	public void setVerificationCodeCheckRequest(VerificationCodeCheckRequest verificationCodeCheckRequest) {
		this.verificationCodeCheckRequest = verificationCodeCheckRequest;
	}
 
}
