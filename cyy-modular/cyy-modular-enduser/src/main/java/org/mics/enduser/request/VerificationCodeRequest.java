package org.mics.enduser.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 发送验证码请求对象
 * @author mics
 * @date 2020年5月19日
 * @version  1.0
 */
@ApiModel(value = "封装验证码对象")
public class VerificationCodeRequest {

	/**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    @Size(min = 4, max = 32, message = "账号必须是4-32位")
    @NotBlank(message = "账号不能为空")
    @Pattern(regexp = "(^1(3|4|5|6|7|8|9)\\d{9}$|[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+\\w{2,14})",message = "账号格式不正确")
    private String account;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

    
}
