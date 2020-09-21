package org.mics.enduser.request;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 手机注册对象
 * @author mics
 * @date 2020年6月10日
 * @version  1.0
 */
@ApiModel("用户注册信息")
public class RegisterRequest {
	
	@ApiModelProperty("账号")
    @Size(min = 4, max = 32, message = "账号必须是4-32位")
    @NotBlank(message = "账号不能为空")
    @Pattern(regexp = "(^1(3|4|5|6|7|8|9)\\d{9}$|[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+\\w{2,14})",message = "账号格式不正确")
    
    private String account;

    @ApiModelProperty("密码")
    @Size(min = 8, max = 18, message = "密码长度为8-18位")
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 短信校验
     */
    @ApiModelProperty("短信校验")
    @Valid
    @NotNull(message = "短信校验不能为空")
    private VerificationCodeCheckRequest verificationCodeCheckRequest;
    
    /**
     * 设备id
     */
    @ApiModelProperty("设备id")
    private String deviceId;



	

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

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
    
    
}
