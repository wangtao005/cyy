package org.mics.sms.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 短信验证码校验
 * @author mics
 * @date 2020年6月10日
 * @version  1.0
 */
@ApiModel("校验短信验证码请求")
public class SmsVerificationCodeCheckRequest {
    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    @NotBlank(message = "手机号不能为空")
    @Size(min = 11, max = 11,message = "手机号必须是11位")
    @Pattern(regexp = "^1(3|4|5|6|7|8|9)\\\\d{9}$")
    private String account;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty("验证码")
    private String code;

    /**
     * 短信验证码记录id
     */
    @NotBlank(message = "短信验证码记录id不能为空")
    @ApiModelProperty("短信验证码记录id")
    private String id;

	

	

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
    

}
