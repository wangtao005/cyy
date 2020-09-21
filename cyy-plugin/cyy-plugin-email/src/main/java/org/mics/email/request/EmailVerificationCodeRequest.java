package org.mics.email.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.mics.email.enums.EmailEnums;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 发送验证码请求对象
 * @author mics
 * @date 2020年5月19日
 * @version  1.0
 */
@ApiModel(value = "封装验证码对象")
public class EmailVerificationCodeRequest {

	/**
     * 手机号
     */
    @ApiModelProperty(value = "邮箱账号")
    @Size(min = 4, max = 32, message = "账号必须是4-32位")
    @NotBlank(message = "邮箱账号不能为空")
    @Pattern(regexp = "[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+\\w{2,14}")
    private String account;

    /**
     * 发送类型(0:登录;1:注册;2:找回密码;3:修改密码)
     */
    @ApiModelProperty(value = "发送类型")
    @NotNull(message = "发送类型不能为空")
    private EmailEnums.CodeType type;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public EmailEnums.CodeType getType() {
		return type;
	}

	public void setType(EmailEnums.CodeType type) {
		this.type = type;
	}
    
}
