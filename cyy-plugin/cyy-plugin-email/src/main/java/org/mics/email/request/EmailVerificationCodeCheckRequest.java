package org.mics.email.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 邮箱验证码校验
 * @author mics
 * @date 2020年6月10日
 * @version  1.0
 */
@ApiModel("校验邮箱验证码请求")
public class EmailVerificationCodeCheckRequest {
    /**
     * 手机号
     */
    @ApiModelProperty("邮箱账号")
    @NotBlank(message = "邮箱账号不能为空")
    @Size(min = 4, max = 32, message = "账号必须是4-32位")
    @Pattern(regexp = "[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+\\w{2,14}")
    private String email;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty("验证码")
    private String code;

    /**
     * 短信验证码记录id
     */
    @NotBlank(message = "邮箱验证码记录id不能为空")
    @ApiModelProperty("邮箱验证码记录id")
    private String id;

	

	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
