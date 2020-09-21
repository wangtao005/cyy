package org.mics.sms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cyy.plugin.sms.verification-code")
public class VerificationCodeProperties {
	 /**
     * 同一手机号申请验证码最小时间间隔 
     */
    private Integer minDuration;

    /**
     * 同一手机每天最大申请验证码数量
     */
    private Integer maxCountPerPhone;

    /**
     * 验证码长度
     */
    private Integer smsLength;
    /**
     * 验证码有效期
     */
    private Integer validDuration;
    
	public Integer getMinDuration() {
		return minDuration;
	}
	public void setMinDuration(Integer minDuration) {
		this.minDuration = minDuration;
	}
	public Integer getMaxCountPerPhone() {
		return maxCountPerPhone;
	}
	public void setMaxCountPerPhone(Integer maxCountPerPhone) {
		this.maxCountPerPhone = maxCountPerPhone;
	}
	public Integer getSmsLength() {
		return smsLength;
	}
	public void setSmsLength(Integer smsLength) {
		this.smsLength = smsLength;
	}
	public Integer getValidDuration() {
		return validDuration;
	}
	public void setValidDuration(Integer validDuration) {
		this.validDuration = validDuration;
	}
    
    

}
