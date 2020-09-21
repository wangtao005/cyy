package org.mics.sms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云短信参数配置
 * @author mics
 * @date 2020年5月19日
 * @version  1.0
 */
@Configuration
@ConfigurationProperties(prefix = "cyy.plugin.sms.alibaba")
@EnableConfigurationProperties(VerificationCodeProperties.class)
public class AliYunSmsProperties{
	 /**
     * 区域编号
     */
    private String regionId;

    /**
     * accessKeyId
     */
    private String accessKeyId;

    /**
     * accessKeySecret
     */
    private String accessKeySecret;

    /**
     * 短信签名
     */
    private String signName;
   
    /**
     * 登录模板code
     */
    private String logInCode;

    /**
     * 注册模板code
     */
    private String registeredCode;

    /**
     * 找回密码模板code
     */
    private String retrievePasswordCode;

    /**
     * 修改密码模板code
     */
    private String modifyPasswordCode;

    /**
     * 普通短信模板code
     */
    private String ordinaryCode;

    
    
    
	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}


	public String getLogInCode() {
		return logInCode;
	}

	public void setLogInCode(String logInCode) {
		this.logInCode = logInCode;
	}

	public String getRegisteredCode() {
		return registeredCode;
	}

	public void setRegisteredCode(String registeredCode) {
		this.registeredCode = registeredCode;
	}

	public String getRetrievePasswordCode() {
		return retrievePasswordCode;
	}

	public void setRetrievePasswordCode(String retrievePasswordCode) {
		this.retrievePasswordCode = retrievePasswordCode;
	}

	public String getModifyPasswordCode() {
		return modifyPasswordCode;
	}

	public void setModifyPasswordCode(String modifyPasswordCode) {
		this.modifyPasswordCode = modifyPasswordCode;
	}

	public String getOrdinaryCode() {
		return ordinaryCode;
	}

	public void setOrdinaryCode(String ordinaryCode) {
		this.ordinaryCode = ordinaryCode;
	}


	
}
