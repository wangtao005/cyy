package org.mics.sms.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.mics.sms.enums.SmsEnums;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 发送验证码请求对象
 * @author mics
 * @date 2020年5月19日
 * @version  1.0
 */
@ApiModel(value = "封装验证码对象")
public class SmsVerificationCodeRequest {

	/**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    @Size(min = 11, max = 11,message = "手机号必须是11位")
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1(3|4|5|6|7|8|9)\\\\d{9}$")
    private String account;

    /**
     * 发送类型(0:登录;1:注册;2:找回密码;3:修改密码)
     */
    @NotNull(message = "发送类型不能为空")
    @ApiModelProperty(value = "发送类型")
    private SmsEnums.CodeType type;

	

	

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public SmsEnums.CodeType getType() {
		return type;
	}

	public void setType(SmsEnums.CodeType type) {
		this.type = type;
	}
    
    
    
}
