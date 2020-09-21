package org.mics.email.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.mail.verification-code")
public class EmailProperties {
    /**
     * 验证码长度
     */
    private Integer codeLength;
    /**
     * 验证码有效期
     */
    private Integer validDuration;
    
    /**
     * 同一邮箱申请验证码最小时间间隔 
     */
    private Integer minDuration;
    /**
     * 同一邮箱每天最大申请验证码数量
     */
    private Integer maxCountPerEmail;
    
    
	public Integer getCodeLength() {
		return codeLength;
	}
	public void setCodeLength(Integer codeLength) {
		this.codeLength = codeLength;
	}
	public Integer getValidDuration() {
		return validDuration;
	}
	public void setValidDuration(Integer validDuration) {
		this.validDuration = validDuration;
	}
	public Integer getMinDuration() {
		return minDuration;
	}
	public void setMinDuration(Integer minDuration) {
		this.minDuration = minDuration;
	}
	public Integer getMaxCountPerEmail() {
		return maxCountPerEmail;
	}
	public void setMaxCountPerEmail(Integer maxCountPerEmail) {
		this.maxCountPerEmail = maxCountPerEmail;
	}
    
    
    
}
