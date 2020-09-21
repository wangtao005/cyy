package org.mics.enduser.request;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 忘记密码请求
 * @author mics
 * @date 2020年6月15日
 * @version  1.0
 */
@ApiModel("忘记密码请求")
public class ForgetRequest {

    /**
     * 手机号
     */
    @Size(min = 4, max = 32, message = "手机号为11位")
    @NotBlank(message = "注册账号不能为空")
    @ApiModelProperty("注册账号")
    private String account;

    /**
     * 新密码
     */
    @Size(min = 8, max = 18, message = "密码长度为8-18位")
    @NotBlank(message = "新密码不能为空")
    @ApiModelProperty("新密码")
    private String password;

    /**
     * 短信校验
     */
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public VerificationCodeCheckRequest getVerificationCodeCheckRequest() {
		return verificationCodeCheckRequest;
	}

	public void setVerificationCodeCheckRequest(VerificationCodeCheckRequest verificationCodeCheckRequest) {
		this.verificationCodeCheckRequest = verificationCodeCheckRequest;
	}


	
    
    
    
}
